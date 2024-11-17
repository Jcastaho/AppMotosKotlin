package com.straccion.appmotos1.presentation.screens.vistadetallesmoto

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.FavoritasUsuarios
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.use_cases.auth.AuthUsesCases
import com.straccion.appmotos1.domain.use_cases.favoritos.FavoritasUsesCase
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class DetallesMotoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val obtenerMotosUsesCase: ObtenerMotosUsesCase,
    private val favoritasUsesCase: FavoritasUsesCase,
    private val authUsesCases: AuthUsesCases

) : ViewModel() {

    private val encodedData = savedStateHandle.get<String>("moto")
    private val encodeIdMoto = savedStateHandle.get<String>("motoId")
    private val busqueda = savedStateHandle.get<String>("busqueda") ?: "false"


    private val _motoById = MutableStateFlow<Response<List<CategoriaMotos>>>(Response.Loading)
    val motoById: StateFlow<Response<List<CategoriaMotos>>> = _motoById.asStateFlow()

    private val _moto = MutableStateFlow<CategoriaMotos>(CategoriaMotos())
    val moto: StateFlow<CategoriaMotos> = _moto.asStateFlow()

    //obtener las motos favoritas
    private val _motosFavoritas = MutableStateFlow<Response<List<FavoritasUsuarios>>>(Response.Loading)
    val motosFavoritas: StateFlow<Response<List<FavoritasUsuarios>>> = _motosFavoritas.asStateFlow()
    val currentUser = authUsesCases.getCurrentUser()?.uid

    private val _selectedColor = MutableStateFlow<String?>(null)
    val selectedColor: StateFlow<String?> = _selectedColor.asStateFlow()

    val displayedImages: StateFlow<List<String>> = moto.combine(selectedColor) { moto, color ->
        when (color) {
            moto.colores.getOrNull(0) -> moto.imagenesPrincipales
            moto.colores.getOrNull(1) -> moto.imagenesColores1
            moto.colores.getOrNull(2) -> moto.imagenesColores2
            moto.colores.getOrNull(3) -> moto.imagenesColores3
            moto.colores.getOrNull(4) -> moto.imagenesColores4
            moto.colores.getOrNull(5) -> moto.imagenesColores5
            moto.colores.getOrNull(6) -> moto.imagenesColores6
            else -> moto.imagenesPrincipales
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun selectColor(color: String) {
        _selectedColor.value = color
    }


    // region infiniteImageCarousel
    private val _selectedImage = MutableStateFlow<Pair<String, Int>?>(null)
    val selectedImage: StateFlow<Pair<String, Int>?> = _selectedImage

    fun selectedImage(image: Pair<String, Int>) {
        _selectedImage.value = image
    }

    fun clearSelectedImage() {
        _selectedImage.value = null
    }
    // endregion

    init {
        loadMotoDetails()
    }
    //region obtener las motos favoritas
    private fun loadMotoDetails() {
        viewModelScope.launch {
            val decodedMoto = decodeMotoData(encodedData)
            if (decodedMoto.colores.isEmpty() || decodedMoto.imagenesPrincipales.isEmpty()) {
                loadMotoById()
            } else {
                _moto.value = decodedMoto
                _selectedColor.value = decodedMoto.colores.firstOrNull()
                _motoById.value = Response.Success(listOf(decodedMoto))
                currentUser?.let { uid ->
                    favoritasUsesCase.obtenerMotosFavoritas(uid ?: "")
                        .collect { response ->
                            _motosFavoritas.value = response
                        }
                }
            }
        }
    }

    private fun loadMotoById() {
        viewModelScope.launch {
            val idMoto = decodeId(encodeIdMoto)
            try {
                obtenerMotosUsesCase.obtenerMotosById(idMoto).collect { response ->
                    _motoById.value = response
                    if (response is Response.Success && response.data.isNotEmpty()) {
                        _moto.value = response.data[0]
                        _selectedColor.value = response.data[0].colores.firstOrNull()
                        currentUser?.let { uid ->
                            favoritasUsesCase.obtenerMotosFavoritas(uid ?: "")
                                .collect { response ->
                                    _motosFavoritas.value = response
                                }
                        }
                    }
                }
            } catch (e: Exception) {
                _motoById.value = Response.Failure(e)
                _motosFavoritas.value = Response.Failure(e)
            }
        }
    }

    private fun decodeMotoData(encodedData: String?): CategoriaMotos {
        return encodedData?.let {
            try {
                CategoriaMotos.fromJson(URLDecoder.decode(it, StandardCharsets.UTF_8.toString()))
            } catch (e: IllegalArgumentException) {
                CategoriaMotos()
            }
        } ?: CategoriaMotos()
    }

    private fun decodeId(encodedId: String?): String {
        return encodedId?.let {
            try {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            } catch (e: IllegalArgumentException) {
                ""
            }
        } ?: ""
    }
    //endregion

    //region Funciones para controlar las favoritas
    fun agregarMotoFav(motoId: String) {
        viewModelScope.launch {
            currentUser?.let { uid ->
                val result = favoritasUsesCase.agregarMotoFav(motoId, uid)
            }
        }
    }

    fun quitarMotoFav(motoId: String, motosFav: List<FavoritasUsuarios>) = viewModelScope.launch {
        currentUser?.let { uid ->
            // Asumiendo que solo necesitas la lista de IDs de motos
            val resultado = motosFav
                .firstOrNull()?.motoId  // Obtiene la lista de motoId del primer elemento
                ?.filter { it != motoId }  // Filtra excluyendo el motoId que queremos quitar
                ?: emptyList()  // Si es null, retorna lista vacía

            val result = favoritasUsesCase.quitarMotosFav(resultado, uid)
        }
    }
    //endregion

    //region funcion para sumar visitas
    fun sumarVisitas(id: String)= viewModelScope.launch {
        obtenerMotosUsesCase.sumarVisitas(id, busqueda.toBoolean())
    }
}



