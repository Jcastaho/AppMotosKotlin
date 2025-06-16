package com.straccion.appmotos1.presentation.navigation.screen.favoritos



sealed class NavFavoritas(val route: String) {
    data object Favoritas: NavFavoritas("favoritas")

}