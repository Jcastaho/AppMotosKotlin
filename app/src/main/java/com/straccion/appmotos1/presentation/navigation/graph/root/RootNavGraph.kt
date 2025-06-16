package com.straccion.appmotos1.presentation.navigation.graph.root

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.straccion.appmotos1.presentation.navigation.Graph
import com.straccion.appmotos1.presentation.navigation.graph.base_de_datos.BaseDatosNavGraph
import com.straccion.appmotos1.presentation.navigation.graph.comparar_motos.CompararNavGraph
import com.straccion.appmotos1.presentation.navigation.graph.estadistica.EstadisticaNavGraph
import com.straccion.appmotos1.presentation.navigation.graph.favoritos.FavoritasNavGraph
import com.straccion.appmotos1.presentation.navigation.graph.inicio.InicioNavGraph
import com.straccion.appmotos1.presentation.navigation.graph.moto_ideal.MiMotoIdealGraph
import com.straccion.appmotos1.presentation.navigation.screen.versusmotos.NavVersusMotos
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.detalles.DetallesMotoScreen
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.versus.VersusMotoScreen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun RootNavGraph(navHostController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ) {
        InicioNavGraph(navHostController = navHostController)
        BaseDatosNavGraph(navHostController = navHostController)
        CompararNavGraph()
        EstadisticaNavGraph(navHostController)
        FavoritasNavGraph(navHostController)
        MiMotoIdealGraph(navHostController)

        // Pantalla compartida fuera de los subgrafos
        composable(
            route = Graph.DETALLES_MOTO,
            arguments = listOf(
                navArgument("moto") { type = NavType.StringType },
                navArgument("busqueda") { type = NavType.BoolType }
            )
        ) {
            DetallesMotoScreen(navHostController)
        }
        composable(
            route = NavVersusMotos.VersusMotos.route,
            arguments = listOf(
                navArgument("moto1") { type = NavType.StringType },
                navArgument("moto2") { type = NavType.StringType },
            )
        ) {
            VersusMotoScreen(navHostController)
        }
    }
}