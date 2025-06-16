package com.straccion.appmotos1.presentation.navigation.screen.versusmotos

import android.net.Uri
import com.google.gson.Gson
import com.straccion.appmotos1.domain.model.CategoriaMotos


sealed class NavVersusMotos(val route: String) {
    data object VersusMotos : NavVersusMotos("versus_motos?moto1={moto1}&moto2={moto2}") {
        fun passMotosVs(moto1: String, moto2: String) =
            "versus_motos?moto1=${Uri.encode(moto1)}&moto2=${Uri.encode(moto2)}"
    }
}
