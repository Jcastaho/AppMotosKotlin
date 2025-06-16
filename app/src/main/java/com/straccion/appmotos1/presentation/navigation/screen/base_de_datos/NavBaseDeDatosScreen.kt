package com.straccion.appmotos1.presentation.navigation.screen.base_de_datos

sealed class NavBaseDeDatosScreen(val route: String) {
    data object BaseDatosInicio: NavBaseDeDatosScreen("basedatos_inicio")
}