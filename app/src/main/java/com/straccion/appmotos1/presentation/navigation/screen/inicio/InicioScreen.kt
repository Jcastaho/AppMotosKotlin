package com.straccion.appmotos1.presentation.navigation.screen.inicio

import android.net.Uri
import com.google.gson.Gson
import com.straccion.appmotos1.domain.model.CategoriaMotos

sealed class InicioScreen(val route: String) {
    data object Inicio: InicioScreen("inicio")
    data object DetallesMoto : InicioScreen("detalles_moto") {
        fun passMoto(moto: CategoriaMotos, busqueda: Boolean) =
            "detalles_moto?moto=${Uri.encode(Gson().toJson(moto))}&busqueda=$busqueda"
    }
}
