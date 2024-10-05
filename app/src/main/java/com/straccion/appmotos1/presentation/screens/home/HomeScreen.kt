package com.straccion.appmotos1.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.straccion.appmotos1.R
import com.straccion.appmotos1.presentation.navigation.DrawerScreen
import com.straccion.appmotos1.presentation.navigation.NavigationDrawerGraph
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navHostController: NavHostController = rememberNavController()) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf<DrawerScreen>(DrawerScreen.Inicio) }

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
                        currentScreen = screen
                        navHostController.navigate(screen.route) {
                            popUpTo(navHostController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ){
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentScreen.title) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavigationDrawerGraph(navHostController)
            }
        }
    }
}

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp)
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
}

@Composable
fun DrawerBody(
    items: List<DrawerScreen>,
    onItemClick: (DrawerScreen) -> Unit
) {
    LazyColumn {
        items(items) { item ->
            NavigationDrawerItem(
                icon = { Icon(item.icon!!, contentDescription = null) },
                label = { Text(item.title) },
                selected = false,
                onClick = { onItemClick(item) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}


