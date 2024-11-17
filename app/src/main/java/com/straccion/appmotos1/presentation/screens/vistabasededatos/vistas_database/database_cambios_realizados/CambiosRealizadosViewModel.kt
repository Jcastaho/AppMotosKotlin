package com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_cambios_realizados

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.straccion.appmotos1.domain.model.MotoDiferencias
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.use_cases.databases.DataBasesUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CambiosRealizadosViewModel @Inject constructor(
    private val dataBasesUsesCase: DataBasesUsesCase
) : ViewModel() {
    private val _datosCambiados = MutableStateFlow<List<MotoDiferencias>>(emptyList())
    val datosCambiados: StateFlow<List<MotoDiferencias>> = _datosCambiados.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        actualizcionesRealizadas()
    }

    fun actualizcionesRealizadas() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = dataBasesUsesCase.actualizacionesRealizadas()
                _datosCambiados.value = result
            } catch (e: Exception) {
                // Manejar errores
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}