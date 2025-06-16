package com.straccion.appmotos1.presentation.navigation.screen.base_de_datos

import android.net.Uri
import com.google.gson.Gson
import com.straccion.appmotos1.domain.model.CategoriaMotos

sealed class NavModificarRegistroScreen(val route: String) {
    data object ModificarRegistro: NavModificarRegistroScreen("modificar_registro")
    data object EditarrRegistro: NavModificarRegistroScreen("editar_registro?moto={moto}"){
        fun passEditarMoto(moto: CategoriaMotos) =
            "editar_registro?moto=${Uri.encode(Gson().toJson(moto))}"
    }
}