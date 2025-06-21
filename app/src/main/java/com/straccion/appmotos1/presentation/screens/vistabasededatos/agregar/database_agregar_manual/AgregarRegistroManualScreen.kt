package com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar_manual

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarRegistroManualScreen(
    navHostController: NavHostController,
    viewModel: AgregarRegistroManualViewModel = hiltViewModel()
) {
    val formState by viewModel.formState.collectAsState()
    Scaffold(
        topBar = {
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.saveRegistro() }) {
                Icon(Icons.Default.Save, contentDescription = "Guardar")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // --- Sección de Datos Generales ---
            FormSectionCard(title = "Datos Generales") {
                FormTextField(label = "Marca de la Moto", value = formState.marcaMoto) {
                    viewModel.onFieldChange("marcaMoto", it)
                }
                FormTextField(label = "Descripción", value = formState.descripcion) {
                    viewModel.onFieldChange("descripcion", it)
                }
            }

            // --- Sección de Precios y Rendimiento ---
            FormSectionCard(title = "Precios y Rendimiento") {
                FormTextField(
                    label = "Precio Actual",
                    value = formState.precioActual,
                    keyboardType = KeyboardType.Number
                ) {
                    viewModel.onFieldChange("precioActual", it)
                }
                FormTextField(
                    label = "Precio Anterior",
                    value = formState.precioAnterior,
                    keyboardType = KeyboardType.Number
                ) {
                    viewModel.onFieldChange("precioAnterior", it)
                }
                FormTextField(
                    label = "Velocidad Máxima (km/h)",
                    value = formState.velocidadMaxima,
                    keyboardType = KeyboardType.Number
                ) {
                    viewModel.onFieldChange("velocidadMaxima", it)
                }
                FormTextField(
                    label = "Consumo por Galón (km)",
                    value = formState.consumoPorGalon,
                    keyboardType = KeyboardType.Number
                ) {
                    viewModel.onFieldChange("consumoPorGalon", it)
                }
                FormTextField(
                    label = "Prioridad",
                    value = formState.prioridad,
                    keyboardType = KeyboardType.Number
                ) {
                    viewModel.onFieldChange("prioridad", it)
                }
            }

            // --- Sección de Banderas (Booleans) ---
            FormSectionCard(title = "Opciones") {
                FormSwitch(label = "Visible en la app", checked = formState.visible) {
                    viewModel.onBooleanChange("visible", it)
                }
                FormSwitch(label = "Tiene descuento", checked = formState.descuento) {
                    viewModel.onBooleanChange("descuento", it)
                }
            }

            // --- Secciones de Listas Dinámicas ---
            DynamicListSection(
                title = "Imágenes Principales (URLs)",
                items = formState.imagenesPrincipales,
                onItemChange = { index, value ->
                    viewModel.updateListItem(
                        "imagenesPrincipales",
                        index,
                        value
                    )
                },
                onAddItem = { viewModel.addToList("imagenesPrincipales") },
                onRemoveItem = { index -> viewModel.removeFromList("imagenesPrincipales", index) }
            )

            DynamicListSection(
                title = "Colores Disponibles",
                items = formState.colores,
                onItemChange = { index, value ->
                    viewModel.updateListItem(
                        "colores",
                        index,
                        value
                    )
                },
                onAddItem = { viewModel.addToList("colores") },
                onRemoveItem = { index -> viewModel.removeFromList("colores", index) }
            )

            // --- Secciones de Mapas Dinámicos ---
            DynamicMapSection(
                title = "Modelos",
                map = formState.modelos,
                keyLabel = "Nombre Modelo",
                valueLabel = "Valor",
                onAddItem = { key, value -> viewModel.addToMap("modelos", key, value) },
                onRemoveItem = { key -> viewModel.removeFromMap("modelos", key) }
            )

            DynamicMapSection(
                title = "Ficha Técnica",
                map = formState.fichaTecnica,
                keyLabel = "Característica",
                valueLabel = "Valor",
                onAddItem = { key, value -> viewModel.addToMap("fichaTecnica", key, value) },
                onRemoveItem = { key -> viewModel.removeFromMap("fichaTecnica", key) }
            )

            Spacer(Modifier.height(80.dp)) // Espacio para que el FAB no tape el último elemento
        }
    }
}

@Composable
fun FormSectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            content()
        }
    }
}

@Composable
fun FormTextField(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true
    )
}

@Composable
fun FormSwitch(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun DynamicListSection(
    title: String,
    items: List<String>,
    onItemChange: (Int, String) -> Unit,
    onAddItem: () -> Unit,
    onRemoveItem: (Int) -> Unit
) {
    FormSectionCard(title = title) {
        items.forEachIndexed { index, item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = item,
                    onValueChange = { onItemChange(index, it) },
                    label = { Text("Item ${index + 1}") },
                    modifier = Modifier.weight(1f)
                )
                if (items.size > 1) {
                    IconButton(onClick = { onRemoveItem(index) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar Item")
                    }
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onAddItem, modifier = Modifier.align(Alignment.End)) {
            Icon(Icons.Default.Add, contentDescription = "Añadir")
            Spacer(Modifier.width(4.dp))
            Text("Añadir Item")
        }
    }
}


@Composable
fun DynamicMapSection(
    title: String,
    map: Map<String, String>,
    keyLabel: String,
    valueLabel: String,
    onAddItem: (String, String) -> Unit,
    onRemoveItem: (String) -> Unit
) {
    var currentKey by remember { mutableStateOf("") }
    var currentValue by remember { mutableStateOf("") }

    FormSectionCard(title = title) {
        // Campos para agregar nuevos pares
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = currentKey,
                onValueChange = { currentKey = it },
                label = { Text(keyLabel) },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            OutlinedTextField(
                value = currentValue,
                onValueChange = { currentValue = it },
                label = { Text(valueLabel) },
                modifier = Modifier.weight(1f)
            )
        }
        Button(
            onClick = {
                onAddItem(currentKey, currentValue)
                currentKey = ""
                currentValue = ""
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp),
            enabled = currentKey.isNotBlank()
        ) {
            Icon(Icons.Default.Add, contentDescription = "Añadir")
            Spacer(Modifier.width(4.dp))
            Text("Añadir")
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        // Lista de pares ya agregados
        if (map.isEmpty()) {
            Text("No hay items.", style = MaterialTheme.typography.bodySmall)
        } else {
            map.forEach { (key, value) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        "$key:",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.weight(0.4f)
                    )
                    Text(
                        value,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(0.6f)
                    )
                    IconButton(onClick = { onRemoveItem(key) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            }
        }
    }
}

