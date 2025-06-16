package com.straccion.appmotos1.presentation.navigation.screen.menulateral


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.straccion.appmotos1.presentation.navigation.Graph
import com.straccion.appmotos1.presentation.navigation.graph.root.RootNavGraph
import com.straccion.appmotos1.presentation.navigation.screen.base_de_datos.NavAgregarRegistroScreen
import com.straccion.appmotos1.presentation.navigation.screen.base_de_datos.NavEliminarRegistro
import com.straccion.appmotos1.presentation.navigation.screen.base_de_datos.NavModificarRegistroScreen
import com.straccion.appmotos1.presentation.navigation.screen.inicio.DrawerScreen
import com.straccion.appmotos1.presentation.navigation.screen.inicio.InicioScreen
import com.straccion.appmotos1.presentation.navigation.screen.versusmotos.NavVersusMotos
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentScreenTitle = getTitleForRoute(currentRoute)

    fun shouldShowBackArrow(): Boolean {
        return currentRoute?.startsWith(InicioScreen.DetallesMoto.route) == true ||
                currentRoute == NavAgregarRegistroScreen.RegistrarMotoWebScrapping.route ||
                currentRoute == NavModificarRegistroScreen.ModificarRegistro.route ||
                currentRoute == NavModificarRegistroScreen.EditarrRegistro.route ||
                currentRoute == NavAgregarRegistroScreen.AgregarRegistro.route ||
                currentRoute == NavVersusMotos.VersusMotos.route ||
                currentRoute == NavEliminarRegistro.EliminarRegistro.route
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.widthIn(max = 290.dp)
            ) {
                DrawerHeader()
                DrawerBody(
                    items = listOf(
                        DrawerScreen.Inicio,
                        DrawerScreen.Base_Datos_Vista,
                        DrawerScreen.Comparar_Motos,
                        DrawerScreen.MiMotoIdeal,
                        DrawerScreen.Favoritas,
                        DrawerScreen.Estadistica
                    ),
                    onItemClick = { screen ->
                        scope.launch { drawerState.close() }
                        navHostController.navigate(screen.route) {
                            popUpTo(Graph.HOME) {
                                inclusive = false
                            } // Control más claro sobre la pila
                            launchSingleTop = true                  // Evita duplicados
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (currentRoute?.startsWith(InicioScreen.DetallesMoto.route) == true) {
                } else {
                    if (shouldShowBackArrow()) {
                        if (currentRoute == NavVersusMotos.VersusMotos.route) {
                            TopAppBar(
                                title = {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            modifier = Modifier.offset(x = (-15).dp),
                                            text = "Versus",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 25.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                },
                                navigationIcon = {
                                    Column(
                                        modifier = Modifier.wrapContentSize(),
                                        verticalArrangement = Arrangement.Center,
                                    ) {
                                        IconButton(onClick = { navHostController.navigateUp() }) {
                                            Icon(
                                                Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = "Regresar"
                                            )
                                        }
                                    }
                                }
                            )
                        } else {
                            TopAppBar(
                                title = { Text(currentScreenTitle) },
                                navigationIcon = {
                                    IconButton(onClick = { navHostController.navigateUp() }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Regresar"
                                        )
                                    }
                                }
                            )
                        }
                    } else {
                        TopAppBar(
                            title = { Text(currentScreenTitle) },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            if (shouldShowBackArrow()) {
                RootNavGraph(
                    navHostController = navHostController,
                    paddingValues = PaddingValues(0.dp)
                )
            } else {
                RootNavGraph(
                    navHostController = navHostController,
                    innerPadding
                )
            }
        }
    }
}

