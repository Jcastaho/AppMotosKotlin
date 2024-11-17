package com.straccion.appmotos1.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.Motorcycle
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.straccion.appmotos1.presentation.screens.vistabasededatos.DataBaseVista
import com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_agregar.AggRegistroScreen
import com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_cambios_realizados.CambiosRealizadosScreen
import com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_eliminar_ocultar.ElimRegistroScreen
import com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_modificar.ModRegistroScreen
import com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.editar_registros.EditarRegistroScreen
import com.straccion.appmotos1.presentation.screens.vistacompararmotos.CompararMotosScreen
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.DetallesMotoScreen
import com.straccion.appmotos1.presentation.screens.vistaestadistica.VistaEstadistica
import com.straccion.appmotos1.presentation.screens.vistafavoritos.FavoritosScreen
import com.straccion.appmotos1.presentation.screens.vistainicio.VistaMotosScreen
import com.straccion.appmotos1.presentation.screens.vistamimotoideal.MMIdealScreen

@Composable
fun NavigationDrawerGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        route = Graph.HOME,
        startDestination = DrawerScreen.Inicio.route
    ){
        composable(route = DrawerScreen.Inicio.route){
            VistaMotosScreen(navHostController)
        }
        composable(
            route = DrawerScreen.Inicio.DetallesMoto.route,
            arguments = listOf(
                navArgument("moto") { type = NavType.StringType },
                navArgument("motoId") { type = NavType.StringType },
                navArgument("busqueda") { type = NavType.StringType }
            )
        ){
            val moto = it.arguments?.getString("moto")
            val motoId = it.arguments?.getString("motoId")

            if (motoId != null && moto != null) {
                DetallesMotoScreen(navHostController)
            }
        }
        composable(route = DrawerScreen.Base_Datos_Vista.route){
            DataBaseVista(navHostController)
        }
        composable(route = DrawerScreen.Base_Datos_Vista.AgregarRegistro.route){
            AggRegistroScreen(navHostController)
        }
        composable(route = DrawerScreen.Base_Datos_Vista.AgregarRegistro.Cambios.route){
            CambiosRealizadosScreen()
        }
        composable(route = DrawerScreen.Base_Datos_Vista.ModificarRegistro.route){
            ModRegistroScreen(navHostController)
        }
        composable(
            route =  DrawerScreen.Base_Datos_Vista.ModificarRegistro.EditarRegistro.route,
            arguments = listOf(
                navArgument("moto") { type = NavType.StringType },
                navArgument("motoId") { type = NavType.StringType }
            )
        ){
            val moto = it.arguments?.getString("moto")
            val motoId = it.arguments?.getString("motoId")

            if (motoId != null && moto != null) {
                EditarRegistroScreen()
            }
        }
        composable(route = DrawerScreen.Base_Datos_Vista.EliminarRegistro.route){
            ElimRegistroScreen(navHostController)
        }
        composable(route = DrawerScreen.Comparar_Motos.route){
            CompararMotosScreen()
        }
        composable(route = DrawerScreen.MiMotoIdeal.route){
            MMIdealScreen(navHostController)
        }
        composable(route = DrawerScreen.Favoritas.route){
            FavoritosScreen(navHostController)
        }
        composable(route = DrawerScreen.Estadistica.route){
            VistaEstadistica()
        }
    }
}
sealed class DrawerScreen (
    val route: String,
    val title: String,
    val icon: ImageVector? = null
){
    data object Inicio: DrawerScreen(
        route = "inicio",
        title = "Inicio",
        icon = Icons.Default.Home
    )
    {
        data object DetallesMoto : DrawerScreen("inicio/detalles/{moto}/{motoId}/{busqueda}", "Detalles Moto"){
            fun passMotos(moto: String, motoId: String, busqueda: Boolean ) = "inicio/detalles/$moto/$motoId/$busqueda"
        }
    }
    data object Base_Datos_Vista: DrawerScreen(
        route = "basedatosvista",
        title = "Bases de Datos",
        icon = Icons.Default.Add
    )
    {
        data object AgregarRegistro : DrawerScreen("basedatosvista/agregarregistro", "Agregar Nuevo Registro"){
            data object Cambios : DrawerScreen("basedatosvista/agregarregistro/cambios", "Cambios Realizados")
        }
        data object ModificarRegistro : DrawerScreen("basedatosvista/modificarregistro", "Modificar Registro"){
            data object EditarRegistro : DrawerScreen("basedatosvista/modificarregistro/editar/{moto}/{motoId}", "Editar Registro"){
                fun passMoto(moto: String, motoId: String ) = "basedatosvista/modificarregistro/editar/$moto/$motoId"
            }
        }
        data object EliminarRegistro : DrawerScreen("base_datos_vista/eliminarRegistro", "Eliminar Registro")
    }
    data object Comparar_Motos: DrawerScreen(
        route = "comparar",
        title = "Comparar Motos",
        icon = Icons.Default.Motorcycle
    )
    data object MiMotoIdeal: DrawerScreen(
        route = "mimotoideal",
        title = "Mi Moto Ideal",
        icon = Icons.Default.QuestionAnswer
    )
    data object Favoritas: DrawerScreen(
        route = "favoritas",
        title = "Favoritas",
        icon = Icons.Default.Star
    )
    data object Estadistica: DrawerScreen(
        route = "estadistica",
        title = "Estadistica",
        icon = Icons.Default.InsertChart
    )
}