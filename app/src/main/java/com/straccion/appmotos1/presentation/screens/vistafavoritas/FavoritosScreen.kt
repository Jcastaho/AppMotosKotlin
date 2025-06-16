package com.straccion.appmotos1.presentation.screens.vistafavoritas

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.presentation.screens.vistafavoritas.components.GetMotosFavoritas
import com.straccion.appmotos1.presentation.screens.vistafavoritas.components.MenuInferiorFav
import com.straccion.appmotos1.presentation.screens.vistafavoritas.components.MenuSuperiorFav
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoritasScreen(
    navHostController: NavHostController,
    viewModel: FavoritosViewModel = hiltViewModel()
) {
    val selectedMotos by viewModel.selectedMotos.collectAsState()

    val isSelectionMode = selectedMotos.isNotEmpty()

    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false,
        confirmValueChange = { newValue ->
            // Permitir ocultar solo cuando no hay selección
            if (!isSelectionMode) {
                true
            } else {
                newValue != SheetValue.Hidden // No permitir ocultar durante la selección
            }
        }
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
    val context = LocalContext.current

    // Controlar el estado del BottomSheet basado en isSelectionMode
    LaunchedEffect(isSelectionMode) {
        if (isSelectionMode) {
            bottomSheetState.partialExpand()
        } else {
            bottomSheetState.hide()
        }
    }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            if (isSelectionMode) {
                MenuInferiorFav(context)
            }
        },
        sheetPeekHeight = if (isSelectionMode) 130.dp else 0.dp,
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
        },
        sheetDragHandle = null, // Esta es la clave para eliminar la línea de arrastre
        sheetSwipeEnabled = !isSelectionMode, //Deshabilita el arrastre cuando hay seleccion.
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            GetMotosFavoritas(navHostController)
        }
    }
}