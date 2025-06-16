package com.straccion.appmotos1.presentation.navigation.screen.menulateral

import com.straccion.appmotos1.presentation.navigation.screen.inicio.DrawerScreen

fun getTitleForRoute(route: String?): String {
    return when (route) {
        DrawerScreen.Inicio.route -> DrawerScreen.Inicio.title
        DrawerScreen.Base_Datos_Vista.route -> DrawerScreen.Base_Datos_Vista.title
        DrawerScreen.Comparar_Motos.route -> DrawerScreen.Comparar_Motos.title
        DrawerScreen.MiMotoIdeal.route -> DrawerScreen.MiMotoIdeal.title
        DrawerScreen.Favoritas.route -> DrawerScreen.Favoritas.title
        DrawerScreen.Estadistica.route -> DrawerScreen.Estadistica.title
        else -> ""
    }
}