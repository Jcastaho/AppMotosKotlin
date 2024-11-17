package com.straccion.appmotos1.presentation.screens.vistaestadistica

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstadisticaViewModel @Inject constructor(
    private val obtenerMotosUsesCase: ObtenerMotosUsesCase
): ViewModel() {

    //region obtener las motos a mostrar
    private val _motos = MutableStateFlow<Response<List<CategoriaMotos>>>(Response.Success(emptyList()))
    val motos: StateFlow<Response<List<CategoriaMotos>>> = _motos.asStateFlow()

    init {
        getMotos()
    }

    private val _motosMasVistas = MutableStateFlow<List<Pair<String, Int>>>(emptyList())
    val motosMasVistas: StateFlow<List<Pair<String, Int>>> = _motosMasVistas.asStateFlow()

    private val _motosMenosVistas = MutableStateFlow<List<Pair<String, Int>>>(emptyList())
    val motosMenosVistas: StateFlow<List<Pair<String, Int>>> = _motosMenosVistas.asStateFlow()

    private val _motosMasBuscadas = MutableStateFlow<List<Pair<String, Int>>>(emptyList())
    val motosMasBuscadas: StateFlow<List<Pair<String, Int>>> = _motosMasBuscadas.asStateFlow()



    //estas son con mas info
    private val _motosMasVistasInfo = MutableStateFlow<List<CategoriaMotos>>(emptyList())
    val motosMasVistasInfo: MutableStateFlow<List<CategoriaMotos>> = _motosMasVistasInfo

    private val _motosMenosVistasInfo = MutableStateFlow<List<CategoriaMotos>>(emptyList())
    val motosMenosVistasInfo: MutableStateFlow<List<CategoriaMotos>> = _motosMenosVistasInfo

    private val _motosMasBuscadasInfo = MutableStateFlow<List<CategoriaMotos>>(emptyList())
    val motosMasBuscadasInfo: MutableStateFlow<List<CategoriaMotos>> = _motosMasBuscadasInfo

    var mostrarDialog1 = mutableStateOf(false)
        private set
    var mostrarDialog2 = mutableStateOf(false)
        private set
    var mostrarDialog3 = mutableStateOf(false)
        private set
    var vistaMaxima = mutableStateOf(0)
        private set
    var busquedaMaxima = mutableStateOf(0)
        private set


    fun getMotos() = viewModelScope.launch {
        _motos.value = Response.Loading
        try {
            obtenerMotosUsesCase.obtenerMotosVisibles().collect { response ->
                _motos.value = response
                if (response is Response.Success) {
                    // Procesar la lista de motos para obtener las más vistas
                    val motosmasVistasLista = response.data
                        .map { moto -> Pair(moto.id, moto.vistas) }  // Crear pares de id y vistas
                        .sortedByDescending { it.second }            // Ordenar por vistas
                        .take(6)
                    _motosMasVistas.value = motosmasVistasLista
                    vistaMaxima.value = _motosMasVistas.value.maxOfOrNull { it.second } ?: 0


                    val motosmasVistasInfoLista = response.data
                        .map { moto -> CategoriaMotos(id = moto.id, vistas = moto.vistas, imagenesPrincipales = moto.imagenesPrincipales) }  // Crear pares de id y vistas
                        .sortedByDescending { it.vistas  }            // Ordenar por vistas
                        .take(15)
                    _motosMasVistasInfo.value = motosmasVistasInfoLista


                    val motosmenosVistasLista = response.data
                        .map { moto -> Pair(moto.id, moto.vistas) }  // Crear pares de id y vistas
                        .sortedBy { it.second }            // Ordenar por vistas
                        .take(6)
                    _motosMenosVistas.value = motosmenosVistasLista

                    val motosmenosVistasInfoLista = response.data
                        .map { moto -> CategoriaMotos(id = moto.id, vistas = moto.vistas, imagenesPrincipales = moto.imagenesPrincipales) }  // Crear pares de id y vistas
                        .sortedBy { it.vistas  }            // Ordenar por vistas
                        .take(15)
                    _motosMenosVistasInfo.value = motosmenosVistasInfoLista

                    val motosmasBuscadasLista = response.data
                        .map { moto -> Pair(moto.id, moto.busquedas) }  // Crear pares de id y vistas
                        .sortedByDescending { it.second }  // Ordenar por vistas
                        .take(10)    // Tomar las 10 primeras
                    _motosMasBuscadas.value = motosmasBuscadasLista
                    busquedaMaxima.value = _motosMasBuscadas.value.maxOfOrNull { it.second } ?: 0


                    val motosmasBuscadasInfoLista = response.data
                        .map { moto -> CategoriaMotos(id = moto.id, busquedas = moto.busquedas, imagenesPrincipales = moto.imagenesPrincipales) }  // Crear pares de id y vistas
                        .sortedByDescending { it.busquedas  }            // Ordenar por vistas
                        .take(15)
                    _motosMasBuscadasInfo.value = motosmasBuscadasInfoLista

                }
            }
        } catch (e: Exception) {
            _motos.value = Response.Failure(e)
        }
    }
    //endregion
}