package com.straccion.appmotos1.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Motorcycle
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.straccion.appmotos1.MotosViewModel
import com.straccion.appmotos1.presentation.screens.vistabasededatos.AggRegistro
import com.straccion.appmotos1.presentation.screens.vistabasededatos.EditarRegistro
import com.straccion.appmotos1.presentation.screens.vistabasededatos.ElimRegistro
import com.straccion.appmotos1.presentation.screens.vistabasededatos.ModRegistro
import com.straccion.appmotos1.presentation.screens.vistabasededatos.VistaBasedeDatos
import com.straccion.appmotos1.presentation.screens.vistacompararmotos.CompararMotosMenu
import com.straccion.appmotos1.presentation.screens.vistaestadistica.VistaEstadistica
import com.straccion.appmotos1.presentation.screens.vistafavoritos.VistaMotosFavoritos
import com.straccion.appmotos1.presentation.screens.vistainicio.VistaMotosScreen
import com.straccion.appmotos1.presentation.screens.vistamimotoideal.VistaPreguntasFiltro


@Composable
fun NavigationDrawerGraph(navHostController: NavHostController, viewModel: MotosViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState() // quitar con el viewmodel
    val questionnaireState by viewModel.questionnaireState.collectAsState() // quitar con el viewmodel
    NavHost(
        navController = navHostController,
        route = Graph.HOME,
        startDestination = DrawerScreen.Inicio.route
    ){
        composable(route = DrawerScreen.Inicio.route){
            VistaMotosScreen()
        }
        composable(route = DrawerScreen.Base_Datos_Vista.route){
            VistaBasedeDatos(navHostController)
        }
        composable(route = DrawerScreen.Base_Datos_Vista.AgregarRegistro.route){
            AggRegistro(viewModel)
        }
        composable(route = DrawerScreen.Base_Datos_Vista.ModificarRegistro.route){
            ModRegistro(state)
        }
        composable(route = DrawerScreen.Base_Datos_Vista.ModificarRegistro.EditarRegistro.route){
            EditarRegistro(viewModel, state)
        }
        composable(route = DrawerScreen.Base_Datos_Vista.EliminarRegistro.route){
            ElimRegistro(state, viewModel)
        }
        composable(route = DrawerScreen.Comparar_Motos.route){
            CompararMotosMenu(state, viewModel)
        }
        composable(route = DrawerScreen.MiMotoIdeal.route){
            VistaPreguntasFiltro(viewModel, questionnaireState)
        }
        composable(route = DrawerScreen.Favoritas.route){
            VistaMotosFavoritos(state, viewModel)
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
    data object Base_Datos_Vista: DrawerScreen(
        route = "base_datos_vista",
        title = "Bases de Datos",
        icon = Icons.Default.Add
    )
    {
        data object AgregarRegistro : DrawerScreen("base_datos_vista/AgregarRegistro", "Agregar Nuevo Registro")
        data object ModificarRegistro : DrawerScreen("base_datos_vista/ModificarRegistro", "Modificar Registro"){
            data object EditarRegistro : DrawerScreen("base_datos_vista/EditarRegistro", "Editar Registro")
        }
        data object EliminarRegistro : DrawerScreen("base_datos_vista/EliminarRegistro", "Eliminar Registro")
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