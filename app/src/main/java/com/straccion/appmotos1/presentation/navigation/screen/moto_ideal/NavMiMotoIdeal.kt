package com.straccion.appmotos1.presentation.navigation.screen.moto_ideal

import android.net.Uri
import com.google.gson.Gson
import com.straccion.appmotos1.domain.model.CategoriaMotos

sealed class NavMiMotoIdeal(val route: String) {
    data object MiMotoIdeal: NavMiMotoIdeal("mi_moto?moto={moto}"){
        fun passEditarMoto(moto: List<CategoriaMotos>) =
            "editar_registro?moto=${Uri.encode(Gson().toJson(moto))}"
    }
}
