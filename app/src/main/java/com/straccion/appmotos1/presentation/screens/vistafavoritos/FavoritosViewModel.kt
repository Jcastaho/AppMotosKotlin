package com.straccion.appmotos1.presentation.screens.vistafavoritos

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.use_cases.auth.AuthUsesCases
import com.straccion.appmotos1.domain.use_cases.favoritos.FavoritasUsesCase
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosUsesCase
import com.straccion.appmotos1.presentation.screens.vistafavoritos.components.CompartirImagenes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritosViewModel @Inject constructor(
    private val authUsesCases: AuthUsesCases,
    private val favoritasUsesCase: FavoritasUsesCase,
    private val obtenerMotosUsesCase: ObtenerMotosUsesCase
) : ViewModel() {

    //obtener las motos a mostrar
    private val _motosFavoritas = MutableStateFlow<Response<List<CategoriaMotos>>>(Response.Loading)
    val motosFavoritas: StateFlow<Response<List<CategoriaMotos>>> = _motosFavoritas.asStateFlow()

    val currentUser = authUsesCases.getCurrentUser()?.uid

    init {
        getMotos()
    }

    private fun getMotos() {
        viewModelScope.launch {
            currentUser?.let { uid ->
                combine(
                    favoritasUsesCase.obtenerMotosFavoritas(uid),
                    obtenerMotosUsesCase.obtenerMotosVisibles()
                ) { favoritosResponse, allMotosResponse ->
                    when {
                        favoritosResponse is Response.Success && allMotosResponse is Response.Success -> {
                            val favoritosIds =
                                favoritosResponse.data.flatMap { it.motoId }.distinct()
                            val allMotos = allMotosResponse.data
                            val favoritasMotos = allMotos.filter { moto -> moto.id in favoritosIds }
                            Response.Success(favoritasMotos)
                        }

                        favoritosResponse is Response.Failure -> Response.Failure(favoritosResponse.exception)
                        allMotosResponse is Response.Failure -> Response.Failure(allMotosResponse.exception)
                        else -> Response.Loading
                    }
                }.collect { response ->
                    _motosFavoritas.value = response
                }
            } ?: run {
                _motosFavoritas.value = Response.Failure(Exception("Usuario no autenticado"))
            }
        }
    }

    //region Selecciones
    private val _selectedMotos = MutableStateFlow<Set<String>>(emptySet())
    val selectedMotos: StateFlow<Set<String>> = _selectedMotos

    fun toggleMotoSelection(motoId: String) {
        _selectedMotos.value = if (_selectedMotos.value.contains(motoId)) {
            _selectedMotos.value - motoId
        } else {
            _selectedMotos.value + motoId
        }
    }

    fun clearSelection() {
        _selectedMotos.value = emptySet()
    }

    fun selectAll() {
        viewModelScope.launch {
            _motosFavoritas.collect { response ->
                when {
                    response is Response.Success -> {
                        val motos: List<CategoriaMotos> = response.data
                        _selectedMotos.value = motos.map { it.id }.toSet()
                    }
                }
            }
        }
    }
    //endregion

    //region compartir Moto
    val motosSeleccionadas: StateFlow<Response<List<CategoriaMotos>>> =
        combine(motosFavoritas, selectedMotos) { favoritas, seleccionadas ->
            when (favoritas) {
                is Response.Success -> {
                    Response.Success(
                        favoritas.data.filter { moto ->
                            moto.id in seleccionadas
                        }
                    )
                }

                is Response.Failure -> Response.Failure(favoritas.exception)
                Response.Loading -> Response.Loading
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Response.Loading
        )

    fun compartir(context: Context, motosSeleccionadas: Response<List<CategoriaMotos>>) {
        viewModelScope.launch {
            try {
                Log.d("ViewModel", "Iniciando compartir...")
                CompartirImagenes(context, motosSeleccionadas)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error al compartir", e)
                // Aquí podrías emitir un estado de error si lo necesitas
            }
        }
    }

    //endregion
    //region Eliminar
    fun update() = viewModelScope.launch {
        val selectedMotos = _selectedMotos.value.toList()
        val motosFavoritasResponse = _motosFavoritas.value

        if (motosFavoritasResponse is Response.Success && currentUser != null) {
            val motosFavoritas = motosFavoritasResponse.data
            // Filtrar las motos que NO están seleccionadas
            val motosNoSeleccionadas = motosFavoritas.filterNot { it.id in selectedMotos }

            // Extraer los IDs de las motos no seleccionadas
            val motosNoSeleccionadasIds = motosNoSeleccionadas.map { it.id }

            // Aquí estamos considerando el caso donde no hay motos no seleccionadas
            val motosAEliminar = if (motosNoSeleccionadasIds.isEmpty()) {
                emptyList() // Eliminar todas las motos si están todas seleccionadas
            } else {
                motosNoSeleccionadasIds
            }
            val result = favoritasUsesCase.quitarMotosFav(motosAEliminar, currentUser)

            if (result is Response.Success) {
                // Limpiar las motos seleccionadas después de una eliminación exitosa
                _selectedMotos.value = emptySet()
            }
        }
    }
    //endregion
}