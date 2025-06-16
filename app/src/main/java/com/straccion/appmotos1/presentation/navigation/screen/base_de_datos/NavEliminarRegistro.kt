package com.straccion.appmotos1.presentation.navigation.screen.base_de_datos

sealed class NavEliminarRegistro (val route: String) {
    data object EliminarRegistro: NavEliminarRegistro("eliminar_registro")
}