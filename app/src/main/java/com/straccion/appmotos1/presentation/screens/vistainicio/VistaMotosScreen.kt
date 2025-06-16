package com.straccion.appmotos1.presentation.screens.vistainicio


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.presentation.components.DefaultOutlinedTextField
import com.straccion.appmotos1.presentation.screens.vistainicio.components.GetMotosInicio

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaMotosScreen(
    navHostController: NavHostController,
    viewModel: VistaInicioViewModel = hiltViewModel()
) {
    val busqueda = viewModel.busqueda
    var expandir by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val bottomSheetGridState = rememberLazyGridState()
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DefaultOutlinedTextField(
                        modifier = Modifier
                            .then(
                                if (expandir)
                                    Modifier.fillMaxWidth()
                                else
                                    Modifier.weight(1f)
                            )
                            .padding(horizontal = 8.dp)
                            .onFocusChanged { focusState ->
                                expandir = focusState.isFocused
                            }
                            .focusRequester(focusRequester),
                        value = busqueda,
                        onValueChange = {
                            viewModel.onSearchQueryChanged(it)
                        },
                        label = "Buscar motos",
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Search
                        )
                    )

                    Icon(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(30.dp)
                            .clickable { showBottomSheet = true },
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filtro",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
                GetMotosInicio(
                    navHostController, onClick = { focusManager.clearFocus() }
                )
            }
        }
    )
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val marcasUnicas = arrayOf("Honda", "Yamaha", "Suzuki", "Bajaj", "Auteco", "Hero")
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    state = bottomSheetGridState,
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(marcasUnicas) { marca ->
                        Text(
                            text = marca,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onSearchQueryChanged(marca)
                                    showBottomSheet = false
                                }
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }

}

