package com.straccion.appmotos1

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Motorcycle
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.straccion.appmotos1.presentation.screens.vistacompararmotos.CompararMotosMenu
import com.straccion.appmotos1.estadistica.VistaEstadistica
import com.straccion.appmotos1.presentation.screens.vistafavoritos.VistaMotosFavoritos
import com.straccion.appmotos1.presentation.screens.vistabasededatos.AggRegistro
import com.straccion.appmotos1.presentation.screens.vistabasededatos.EditarRegistro
import com.straccion.appmotos1.presentation.screens.vistabasededatos.ElimRegistro
import com.straccion.appmotos1.presentation.screens.vistabasededatos.ModRegistro
import com.straccion.appmotos1.presentation.screens.vistabasededatos.VistaBasedeDatos
import com.straccion.appmotos1.presentation.screens.vistamimotoideal.VistaPreguntasFiltro
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun PantallaInicial(navController: NavHostController) {
    val viewModel: MotosViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val questionnaireState by viewModel.questionnaireState.collectAsState()

    var tituloSuperior by remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .widthIn(max = 290.dp)
                    .padding(
                        bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateBottomPadding()
                    )
            ) {
                LazyColumn {
                    item {
                        Column(modifier = Modifier.padding(start = 15.dp)) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(start = 15.dp, top = 20.dp, bottom = 30.dp)

                            ) {
                                Image(
                                    painterResource(
                                        id = R.drawable.icon_app
                                    ),
                                    modifier = Modifier
                                        .size(148.dp)
                                        .offset(x = (-15).dp, y = (-5).dp)
                                        .align(Alignment.Center)
                                        .clip(CircleShape),
                                    contentDescription = ""
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                        tituloSuperior = "Inicio"
                                        navController.navigate(Pantallas.PantallaPrincipal.route)
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(27.dp)
                                        .align(Alignment.CenterVertically)
                                        .padding(end = 8.dp)
                                )
                                Text("Inicio", modifier = Modifier.padding(16.dp))
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                        tituloSuperior = "Base de Datos"
                                        navController.navigate(Pantallas.BasedeDatos.route)
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(27.dp)
                                        .align(Alignment.CenterVertically)
                                        .padding(end = 8.dp)
                                )
                                Text("Base de Datos", modifier = Modifier.padding(16.dp))
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                        tituloSuperior = "Comparar Motos"
                                        navController.navigate(Pantallas.CompararMotos.route)
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Motorcycle,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(27.dp)
                                        .align(Alignment.CenterVertically)
                                        .padding(end = 8.dp)
                                )
                                Text("Comparar Motos", modifier = Modifier.padding(16.dp))
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                        tituloSuperior = "Mi Moto Ideal"
                                        navController.navigate(Pantallas.VistaPreguntas.route)
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.QuestionAnswer,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(27.dp)
                                        .align(Alignment.CenterVertically)
                                        .padding(end = 8.dp)
                                )
                                Text("Mi Moto Ideal", modifier = Modifier.padding(16.dp))
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                        tituloSuperior = "Favoritas"
                                        navController.navigate(Pantallas.Favoritas.route)
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(27.dp)
                                        .align(Alignment.CenterVertically)
                                        .padding(end = 8.dp)
                                )
                                Text("Favoritas", modifier = Modifier.padding(16.dp))
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                        tituloSuperior = "Estadistica"
                                        navController.navigate(Pantallas.Estadisticas.route)
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.InsertChart,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(27.dp)
                                        .align(Alignment.CenterVertically)
                                        .padding(end = 8.dp)
                                )
                                Text("Estadistica", modifier = Modifier.padding(16.dp))
                            }
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.offset(y = (5).dp),
                    title = {
                        Text(
                            tituloSuperior.takeIf { it.isNotEmpty() } ?: "Mi Moto Ideal",
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Abrir menú"
                            )
                        }
                    },
                    actions = {
                        // Mostrar un ícono solo en la pantalla PantallaDetalles
                        if (currentRoute == Pantallas.PantallaDetallesMoto.route) {
                            if (state.selectedMotos != null) {
                                state.selectedMotos?.let { moto ->
                                    val isFavorite by viewModel.isFavorite.collectAsState()
                                    IconButton(
                                        onClick = {
                                            viewModel.toggleFavorite(moto.id)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (moto.favoritos) Icons.Filled.Star else Icons.Filled.StarBorder,
                                            contentDescription = if (moto.favoritos) "Quitar de favoritos" else "Agregar a favoritos"
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(
                    navController = navController,
                    startDestination = Pantallas.PantallaPrincipal.route
                ) {
                    composable(Pantallas.PantallaPrincipal.route) {
                        viewModel.ReiniciarQuestionnaire()
                        VistaMotos(
                            state = state,
                            onMotoClick = { moto ->
                                viewModel.selectMoto(moto)
                                navController.navigate(
                                    Pantallas.PantallaDetallesMoto.createRoute(
                                        moto.id
                                    )
                                )
                            },
                            onSearch = viewModel::updateSearchQuery
                        )
                    }
                    composable(
                        route = Pantallas.PantallaDetallesMoto.route,
                    ) { backStackEntry ->
                        val motoId = backStackEntry.arguments?.getString("motoId")

                        LaunchedEffect(motoId) {
                            motoId?.let { id ->
                                viewModel.selectMotoById(id)
                            }
                        }

                        if (state.selectedMotos != null) {
                            PantallaDetalles(viewModel = viewModel)
                        } else {
                            // Manejar el caso en que no se encuentra la moto
                            Text("Moto no encontrada")
                        }
                    }
                    composable(route = Pantallas.BasedeDatos.route) {
                        VistaBasedeDatos(navController = navController)
                    }
                    composable(route = Pantallas.AggRegistro.route) {
                        AggRegistro(viewModel = viewModel)
                    }
                    composable(route = Pantallas.ModRegistro.route) {
                        ModRegistro(
                            state = state,
                            onMotoClick = { moto ->
                                viewModel.selectMoto(moto)
                                navController.navigate(
                                    Pantallas.EditarRegistro.Route(
                                        moto.id
                                    )
                                )
                            },
                            onSearch = viewModel::updateSearchQuery
                        )
                    }
                    composable(route = Pantallas.ElimRegistro.route) {
                        ElimRegistro(
                            state = state,
                            viewModel = viewModel,
                            onSearch = viewModel::busquedaBarraEliminarBaseDatos,
                        )
                    }
                    composable(
                        route = Pantallas.EditarRegistro.route,
                        arguments = listOf(navArgument("motoId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val motoId = backStackEntry.arguments?.getString("motoId")

                        LaunchedEffect(motoId) {
                            motoId?.let { id ->
                                viewModel.selectMotoById(id)
                            }
                        }
                        if (state.selectedMotos != null) {
                            EditarRegistro(viewModel = viewModel, state = state)
                        } else {
                            // Manejar el caso en que no se encuentra la moto
                            Text("Moto no encontrada")
                        }
                    }
                    composable(route = Pantallas.VistaPreguntas.route) {
                        VistaPreguntasFiltro(
                            viewModel = viewModel,
                            questionnaireState = questionnaireState,
                            onMotoClick = { moto ->
                                viewModel.selectMoto(moto)
                                navController.navigate(
                                    Pantallas.PantallaDetallesMoto.createRoute(moto.id)
                                )
                            },
                        )
                    }
                    composable(route = Pantallas.CompararMotos.route) {
                        CompararMotosMenu(
                            state = state,
                            viewModel = viewModel
                        )
                    }
                    composable(route = Pantallas.Favoritas.route) {
                        VistaMotosFavoritos(
                            state = state,
                            onMotoClick = { moto ->
                                viewModel.selectMoto(moto)
                                navController.navigate(
                                    Pantallas.PantallaDetallesMoto.createRoute(moto.id)

                                )
                            },
                            viewModel = viewModel
                        )
                    }
                    composable(route = Pantallas.Estadisticas.route) {
                        VistaEstadistica(
                            state = state,
                            onMotoClick = { moto ->
                                viewModel.selectMoto(moto)
                                navController.navigate(
                                    Pantallas.PantallaDetallesMoto.createRoute(moto.id)

                                )
                            },
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}
