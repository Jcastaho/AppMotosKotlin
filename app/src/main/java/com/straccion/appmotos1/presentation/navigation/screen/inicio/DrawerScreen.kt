package com.straccion.appmotos1.presentation.navigation.screen.inicio

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.Motorcycle
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.straccion.appmotos1.presentation.navigation.Graph

sealed class DrawerScreen (
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Inicio : DrawerScreen(
        route = "inicio",
        title = "Inicio",
        icon = Icons.Default.Home
    )
    data object Base_Datos_Vista: DrawerScreen(
        route = "basedatos_inicio",
        title = "Bases de Datos",
        icon = Icons.Default.Add
    )
    data object Comparar_Motos: DrawerScreen(
        route = "comparar_motos",
        title = "Comparar Motos",
        icon = Icons.Default.Motorcycle
    )
    data object MiMotoIdeal: DrawerScreen(
        route = "mi_moto",
        title = "Mi Moto Ideal",
        icon = Icons.Default.QuestionAnswer
    )
    data object Favoritas: DrawerScreen(
        route = "favoritas",
        title = "Favoritas",
        icon = Icons.Default.Star
    )
    data object Estadistica: DrawerScreen(
        route = "estadisticas",
        title = "Estadistica",
        icon = Icons.Default.InsertChart
    )
}