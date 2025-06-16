package com.straccion.appmotos1.presentation.navigation.graph.comparar_motos

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.straccion.appmotos1.presentation.navigation.Graph
import com.straccion.appmotos1.presentation.navigation.screen.comparar_motos.NavCompararMotos
import com.straccion.appmotos1.presentation.screens.vistacompararmotos.CompararMotosScreen

fun NavGraphBuilder.CompararNavGraph() {
    navigation(
        route = Graph.COMPARAR_MOTOS,
        startDestination = NavCompararMotos.CompararMotos.route
    ){
        composable(route = NavCompararMotos.CompararMotos.route) {
            CompararMotosScreen()
        }
    }
}