package com.straccion.appmotos1.presentation.navigation.graph.estadistica

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.straccion.appmotos1.presentation.navigation.Graph
import com.straccion.appmotos1.presentation.navigation.screen.estadistica.NavEstadisticaScreen
import com.straccion.appmotos1.presentation.screens.vistaestadistica.VistaEstadistica

fun NavGraphBuilder.EstadisticaNavGraph(navHostController: NavHostController) {
    navigation(
    route = Graph.ESTADISTICA,
    startDestination = NavEstadisticaScreen.Estadisticas.route
    ){
        composable(route = NavEstadisticaScreen.Estadisticas.route) {
            VistaEstadistica()
        }
    }
}