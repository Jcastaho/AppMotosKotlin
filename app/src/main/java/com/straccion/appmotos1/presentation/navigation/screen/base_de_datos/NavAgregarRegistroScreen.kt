package com.straccion.appmotos1.presentation.navigation.screen.base_de_datos

sealed class NavAgregarRegistroScreen(val route: String) {
    data object AgregarRegistro: NavAgregarRegistroScreen("agregar_registro")
    data object RegistrarMotoManual: NavAgregarRegistroScreen("registrar_motomanual")
    data object RegistrarMotoWebScrapping: NavAgregarRegistroScreen("registrar_moto_webscrapping")
}