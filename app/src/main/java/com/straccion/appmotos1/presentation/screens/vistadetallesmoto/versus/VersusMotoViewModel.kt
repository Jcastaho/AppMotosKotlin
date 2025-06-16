package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.versus

import androidx.lifecycle.SavedStateHandle
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
class VersusMotoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val obtenerMotosUsesCase: ObtenerMotosUsesCase,
): ViewModel() {

    private val recibirMoto1: String = checkNotNull(savedStateHandle["moto1"])
    private val recibirMoto2: String = checkNotNull(savedStateHandle["moto2"])



    private val _moto1 = MutableStateFlow<Response<CategoriaMotos>>(Response.Loading)
    val moto1: StateFlow<Response<CategoriaMotos>> = _moto1.asStateFlow()

    private val _moto2 = MutableStateFlow<Response<CategoriaMotos>>(Response.Loading)
    val moto2: StateFlow<Response<CategoriaMotos>> = _moto2.asStateFlow()

    
    init {
        loadMotoById()
    }

    private fun loadMotoById() {
        viewModelScope.launch {
            _moto1.value = Response.Loading
            _moto2.value = Response.Loading
            launch {
                try {
                    obtenerMotosUsesCase.obtenerMotosById(recibirMoto1).collect { response ->
                        if (response is Response.Success && response.data.isNotEmpty()) {
                            _moto1.value = Response.Success(response.data.first())
                        } else if (response is Response.Failure) {
                            _moto1.value = Response.Failure(response.exception)
                        }
                    }
                } catch (e: Exception) {
                    _moto1.value = Response.Failure(e)
                }
            }

            launch {
                try {
                    obtenerMotosUsesCase.obtenerMotosById(recibirMoto2).collect { response ->
                        if (response is Response.Success && response.data.isNotEmpty()) {
                            _moto2.value = Response.Success(response.data.first())
                        } else if (response is Response.Failure) {
                            _moto2.value = Response.Failure(response.exception)
                        }
                    }
                } catch (e: Exception) {
                    _moto2.value = Response.Failure(e)
                }
            }
        }
    }
}