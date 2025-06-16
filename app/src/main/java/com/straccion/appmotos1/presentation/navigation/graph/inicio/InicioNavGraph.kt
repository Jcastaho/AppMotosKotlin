package com.straccion.appmotos1.presentation.navigation.graph.inicio

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.straccion.appmotos1.presentation.navigation.Graph
import com.straccion.appmotos1.presentation.navigation.screen.inicio.InicioScreen
import com.straccion.appmotos1.presentation.screens.vistainicio.VistaMotosScreen

fun NavGraphBuilder.InicioNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.HOME,
        startDestination = InicioScreen.Inicio.route
    ) {
        composable(route = InicioScreen.Inicio.route) {
            VistaMotosScreen(navHostController)
        }
    }
}