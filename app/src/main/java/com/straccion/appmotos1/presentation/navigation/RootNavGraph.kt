package com.straccion.appmotos1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.straccion.appmotos1.presentation.screens.home.HomeScreen

@Composable
fun RootNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ){
        composable(route = Graph.HOME){
            HomeScreen()
        }
    }
}