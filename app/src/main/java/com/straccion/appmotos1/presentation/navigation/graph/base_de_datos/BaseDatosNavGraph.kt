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
import com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar_manual.SubirImagenesScreen
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
            // 2. NUEVA PANTALLA: Subir Imágenes. Recibe nombre y categoría.
            composable(
                route = NavAgregarRegistroScreen.SubirImagenes.route,
                arguments = listOf(
                    navArgument("nombreMoto") { type = NavType.StringType },
                    navArgument("categoria") { type = NavType.StringType },
                    navArgument("marca") { type = NavType.StringType },
                    navArgument("fabricante") { type = NavType.StringType }
                )
            ) {
                // Aquí va la pantalla para seleccionar las fotos
                SubirImagenesScreen(navController = navHostController)
            }

            // 3. NUEVA PANTALLA: Formulario final. Recibe todas las URLs.
            composable(
                route = NavAgregarRegistroScreen.FormularioFinal.route,
                arguments = listOf(
                    navArgument("nombreMoto") { type = NavType.StringType },
                    navArgument("principales") { type = NavType.StringType },
                    navArgument("caracteristicas") { type = NavType.StringType },
                    navArgument("colores") { type = NavType.StringType },
                    // Los argumentos para las imágenes por color son opcionales
                    navArgument("imagenesColores1") { type = NavType.StringType; nullable = true },
                    navArgument("imagenesColores2") { type = NavType.StringType; nullable = true },
                    navArgument("imagenesColores3") { type = NavType.StringType; nullable = true },
                    navArgument("imagenesColores4") { type = NavType.StringType; nullable = true },
                    navArgument("imagenesColores5") { type = NavType.StringType; nullable = true },
                    navArgument("imagenesColores6") { type = NavType.StringType; nullable = true },
                )
            ) {
                // Aquí va la pantalla del formulario que te di primero
                AgregarRegistroManualScreen(navHostController = navHostController)
            }
//            composable(route = NavAgregarRegistroScreen.RegistrarMotoManual.route) {
//                SubirImagenesScreen(navHostController)
//              //  AgregarRegistroManualScreen(navHostController)
//            }
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