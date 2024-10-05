package com.straccion.appmotos1.presentation.screens.vistadetallesmoto

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.straccion.appmotos1.domain.model.CategoriaMotos
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class DetallesMotoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel(){
    private val encodedData = savedStateHandle.get<String>("motoId")
    val moto = encodedData?.let { CategoriaMotos.fromJson(URLDecoder.decode(it, StandardCharsets.UTF_8.toString())) } ?: CategoriaMotos()


    var selectedColor: String? by mutableStateOf(moto.colores.firstOrNull())
    val displayedImages: List<String>
        get() = when (selectedColor) {
            moto.colores.getOrNull(0) -> moto.imagenesPrincipales
            moto.colores.getOrNull(1) -> moto.imagenesColores1
            moto.colores.getOrNull(2) -> moto.imagenesColores2
            moto.colores.getOrNull(3) -> moto.imagenesColores3
            moto.colores.getOrNull(4) -> moto.imagenesColores4
            moto.colores.getOrNull(5) -> moto.imagenesColores5
            moto.colores.getOrNull(6) -> moto.imagenesColores6
            else -> moto.imagenesPrincipales
        }

    fun imagenesMostradas(color: String){
        val imagenesMostradas = when (color) {
            moto.colores.getOrNull(0) -> moto.imagenesPrincipales
            moto.colores.getOrNull(1) -> moto.imagenesColores1
            moto.colores.getOrNull(2) -> moto.imagenesColores2
            moto.colores.getOrNull(3) -> moto.imagenesColores3
            moto.colores.getOrNull(4) -> moto.imagenesColores4
            moto.colores.getOrNull(5) -> moto.imagenesColores5
            moto.colores.getOrNull(6) -> moto.imagenesColores6
            else -> moto.imagenesPrincipales
        }
    }


}