package com.straccion.appmotos1.presentation.navigation.graph.favoritos

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.straccion.appmotos1.presentation.navigation.Graph
import com.straccion.appmotos1.presentation.navigation.screen.favoritos.NavFavoritas
import com.straccion.appmotos1.presentation.screens.vistafavoritas.FavoritasScreen

fun NavGraphBuilder.FavoritasNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.FAVORITAS,
        startDestination = NavFavoritas.Favoritas.route
    ){
        composable(route = NavFavoritas.Favoritas.route){
            FavoritasScreen(navHostController)
        }


    }
}
