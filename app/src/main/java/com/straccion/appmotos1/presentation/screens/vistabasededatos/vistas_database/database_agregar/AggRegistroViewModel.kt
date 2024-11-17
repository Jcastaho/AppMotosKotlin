package com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_agregar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.use_cases.databases.DataBasesUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AggRegistroViewModel @Inject constructor(
    private val dataBasesUsesCase: DataBasesUsesCase
) : ViewModel() {
    // obtener las motos y las listas
    private val _motosResponse = MutableStateFlow<Response<List<CategoriaMotos>>>(Response.Success(emptyList()))
    val motosResponse: StateFlow<Response<List<CategoriaMotos>>> = _motosResponse.asStateFlow()

    private val _listaFabricantes = MutableStateFlow<List<String>>(emptyList())
    val listaFabricantes: StateFlow<List<String>> = _listaFabricantes.asStateFlow()

    private val _listaMarcas = MutableStateFlow<List<String>>(emptyList())
    val listaMarcas: StateFlow<List<String>> = _listaMarcas.asStateFlow()

    private val _listaCategorias = MutableStateFlow<List<String>>(emptyList())
    val listaCategorias: StateFlow<List<String>> = _listaCategorias.asStateFlow()
    // obtener las motos y las listas---------------------------------------------------


    //ventanas emergentes con las listas
    private var _dialogResultWebScraping = MutableStateFlow(false)
    val dialogResultWebScraping = _dialogResultWebScraping.asStateFlow()

    private var _fabricanteText = MutableStateFlow(Pair(false, ""))
    var fabricanteText = _fabricanteText.asStateFlow()

    private var _marcaText = MutableStateFlow(Pair(false, ""))
    var marcaText = _marcaText.asStateFlow()

    private var _categoriaText = MutableStateFlow(Pair(false, ""))
    var categoriaText = _categoriaText.asStateFlow()

    private var _mostrarProgresBar = MutableStateFlow(false)
    var mostrarProgresBar = _mostrarProgresBar.asStateFlow()

    private var _nombreMotoaRegistrar = MutableStateFlow("")
    var nombreMotoaRegistrar = _nombreMotoaRegistrar.asStateFlow()
    //ventanas emergentes con las listas-------------------------------------------------

    //webScraping
    private val _motosUpdateState = MutableStateFlow<Response<String>>(Response.Loading)
    val motosUpdateState: StateFlow<Response<String>> = _motosUpdateState.asStateFlow()
    //webScraping-----------------------------------------------


    init {
        getMotos()
    }

    //region obtener las motos y las listas
    private fun getMotos() = viewModelScope.launch {
        _motosResponse.value = Response.Loading
        try {
            dataBasesUsesCase.obtenerAllMotos().collect { response ->
                _motosResponse.value = response
                if (response is Response.Success) {
                    // Actualiza la lista de fabricantes únicos
                    val fabricantes = response.data
                        .mapNotNull { it.ubicacionImagenes["carpeta1"] } // Extrae los fabricantes
                        .toSet() // Elimina duplicados
                        .toList() // Convierte a lista
                    _listaFabricantes.value = fabricantes

                    val marcas = response.data
                        .mapNotNull { it.ubicacionImagenes["carpeta2"] } // Extrae los fabricantes
                        .toSet() // Elimina duplicados
                        .toList() // Convierte a lista
                    _listaMarcas.value = marcas

                    val categorias = response.data
                        .mapNotNull { it.ubicacionImagenes["carpeta3"] } // Extrae los fabricantes
                        .toSet() // Elimina duplicados
                        .toList() // Convierte a lista
                    _listaCategorias.value = categorias
                }
            }
        } catch (e: Exception) {
            _motosResponse.value = Response.Failure(e)
        }
        actualizarMotos()
    }
//endregion

    //region ventanas emergentes con las listas
    fun hideDialogWebScraping() {
        _dialogResultWebScraping.value = false
    }

    fun showDialogWebScraping() {
        _dialogResultWebScraping.value = true
    }

    fun mostrarFabricante() {
        _fabricanteText.value = _fabricanteText.value.copy(
            first = true,
            second = ""
        )
    }

    fun ocultarFabricante(dato: String = "") {
        _fabricanteText.value = _fabricanteText.value.copy(
            first = false,
            second = dato
        )
    }

    fun mostrarMarca() {
        _marcaText.value = _marcaText.value.copy(
            first = true,
            second = ""
        )
    }

    fun ocultarMarca(dato: String = "") {
        _marcaText.value = _marcaText.value.copy(
            first = false,
            second = dato
        )
    }

    fun mostrarCategoria() {
        _categoriaText.value = _categoriaText.value.copy(
            first = true,
            second = ""
        )
    }

    fun ocultarCategoria(dato: String = "") {
        _categoriaText.value = _categoriaText.value.copy(
            first = false,
            second = dato
        )
    }

    fun mostrarProgress() {
        _mostrarProgresBar.value = true
    }

    fun ocultarProgress() {
        _mostrarProgresBar.value = false
    }

    fun nombreMoto(valor: String) {
        _nombreMotoaRegistrar.value = valor
    }

    //endregion

    //region webScraping
    fun actualizarMotos() {
        viewModelScope.launch {
            _motosUpdateState.value = Response.Loading // Muestra el estado de carga
            val result = dataBasesUsesCase.actualizarMotos() // Llama al repositorio para actualizar
            _motosUpdateState.value = result // Actualiza el estado con el resultado
        }
    }
    //endregion
}
