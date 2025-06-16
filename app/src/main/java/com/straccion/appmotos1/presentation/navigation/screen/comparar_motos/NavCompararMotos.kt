package com.straccion.appmotos1.presentation.navigation.screen.comparar_motos

sealed class NavCompararMotos(val route: String) {
    data object CompararMotos: NavCompararMotos("comparar_motos")
}
