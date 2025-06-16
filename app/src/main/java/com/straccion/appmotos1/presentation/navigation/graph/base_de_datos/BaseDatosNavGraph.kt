package com.straccion.appmotos1.presentation.navigation.graph.base_de_datos

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.straccion.appmotos1.presentation.navigation.Graph
import com.straccion.appmotos1.presentation.navigation.screen.base_de_datos.NavAgregarRegistroScreen
import com.straccion.appmotos1.presentation.navigation.screen.base_de_datos.NavBaseDeDatosScreen
import com.straccion.appmotos1.presentation.navigation.screen.base_de_datos.NavEliminarRegistro
import com.straccion.appmotos1.presentation.navigation.screen.base_de_datos.NavModificarRegistroScreen
import com.straccion.appmotos1.presentation.screens.vistabasededatos.BaseDatosScreen
import com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar.AggRegistroScreen
import com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar_manual.AgregarRegistroManualScreen
import com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_cambios_realizados.CambiosRealizadosScreen
import com.straccion.appmotos1.presentation.screens.vistabasededatos.eliminar_registros.ElimRegistroScreen
import com.straccion.appmotos1.presentation.screens.vistabasededatos.modificar.database_modificar.ModRegistroScreen
import com.straccion.appmotos1.presentation.screens.vistabasededatos.modificar.editar_registros.EditarRegistroScreen


fun NavGraphBuilder.BaseDatosNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.BASE_DATOS,
        startDestination = NavBaseDeDatosScreen.BaseDatosInicio.route
    ){
        composable(route = NavBaseDeDatosScreen.BaseDatosInicio.route) {
            BaseDatosScreen(navHostController)
        }

        navigation(
            route = Graph.AGREGAR_REGISTRO,
            startDestination = NavAgregarRegistroScreen.AgregarRegistro.route
        ) {
            composable(route = NavAgregarRegistroScreen.AgregarRegistro.route) {
                AggRegistroScreen(navHostController)
            }
            composable(route = NavAgregarRegistroScreen.RegistrarMotoManual.route) {
                AgregarRegistroManualScreen(navHostController)
            }
            composable(route = NavAgregarRegistroScreen.RegistrarMotoWebScrapping.route) {
                CambiosRealizadosScreen()
            }
        }

        composable(route = NavModificarRegistroScreen.ModificarRegistro.route) {
            ModRegistroScreen(navHostController)
        }

        composable(
            route = NavModificarRegistroScreen.EditarrRegistro.route,
            arguments = listOf(navArgument("moto") { type = NavType.StringType })
        ) {
            EditarRegistroScreen()
        }
        composable(route = NavEliminarRegistro.EliminarRegistro.route) {
            ElimRegistroScreen()
        }

    }
}