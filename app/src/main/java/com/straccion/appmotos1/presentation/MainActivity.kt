package com.straccion.appmotos1.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.straccion.appmotos1.presentation.navigation.screen.menulateral.MainScreen
import com.straccion.appmotos1.presentation.ui.theme.Appmotos1Theme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Appmotos1Theme {
                navHostController = rememberNavController()
                MainScreen(navHostController)
            }
        }
    }
}
