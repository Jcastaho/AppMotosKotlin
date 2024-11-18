package com.straccion.appmotos1.presentation.screens.vistafavoritos

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.presentation.screens.vistafavoritos.components.GetMotosFavoritas
import com.straccion.appmotos1.presentation.screens.vistafavoritos.components.MenuInferiorFav
import com.straccion.appmotos1.presentation.screens.vistafavoritos.components.MenuSuperiorFav


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoritosScreen(
    navHostController: NavHostController,
    viewModel: FavoritosViewModel = hiltViewModel()
) {
    val selectedMotos by viewModel.selectedMotos.collectAsState()
    val isSelectionMode = selectedMotos.isNotEmpty()

    val sheetState = rememberBottomSheetScaffoldState()
    val context = LocalContext.current

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {
           MenuInferiorFav(context)
        },
        sheetPeekHeight = if (isSelectionMode) 110.dp else 0.dp,

        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (isSelectionMode) {
                    MenuSuperiorFav(
                        selectedCount = selectedMotos.size,
                        onCancelSelection = { viewModel.clearSelection() },
                        onSelectAll = { viewModel.selectAll() }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            GetMotosFavoritas(navHostController)
        }
    }
}