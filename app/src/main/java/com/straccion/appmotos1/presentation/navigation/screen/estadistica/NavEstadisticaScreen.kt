package com.straccion.appmotos1.presentation.navigation.screen.estadistica

sealed class NavEstadisticaScreen(val route: String) {
    data object Estadisticas: NavEstadisticaScreen("estadisticas")
}