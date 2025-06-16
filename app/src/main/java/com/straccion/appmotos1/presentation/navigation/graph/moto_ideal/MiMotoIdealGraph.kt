package com.straccion.appmotos1.presentation.navigation.graph.moto_ideal

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.straccion.appmotos1.presentation.navigation.Graph
import com.straccion.appmotos1.presentation.navigation.screen.moto_ideal.NavMiMotoIdeal
import com.straccion.appmotos1.presentation.screens.vistamimotoideal.MiMotoIdealScreen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
fun NavGraphBuilder.MiMotoIdealGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.MI_MOTO_IDEAL,
        startDestination = NavMiMotoIdeal.MiMotoIdeal.route
    ){
        composable(route = NavMiMotoIdeal.MiMotoIdeal.route){
            MiMotoIdealScreen(navHostController)
        }
    }
}