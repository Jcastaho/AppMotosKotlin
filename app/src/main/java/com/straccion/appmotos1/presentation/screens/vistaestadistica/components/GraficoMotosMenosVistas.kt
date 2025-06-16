package com.straccion.appmotos1.presentation.screens.vistaestadistica.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.straccion.appmotos1.presentation.screens.vistaestadistica.EstadisticaViewModel

@Composable
fun GraficoMotosMenosVistas(
    listaMenosVistas: List<Pair<String, Int>>,
    viewModel: EstadisticaViewModel = hiltViewModel()
) {
    var showDialog by viewModel.mostrarDialog2
    val motosMenosVistasInfo by viewModel.motosMenosVistasInfo.collectAsState()
    val backgroundColor = MaterialTheme.colorScheme.background.toArgb()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 15.dp),
            text = "Motos Menos Vistas",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize(),
                factory = { context ->
                    PieChart(context).apply {
                        description.isEnabled = false
                        isDrawHoleEnabled = true
                        holeRadius = 58f
                        legend.isEnabled = false // Deshabilitamos la leyenda inferior
                        setDrawEntryLabels(true)
                        setDrawMarkers(true)
                        setHoleColor(backgroundColor)
                        // Animación opcional
                        animateY(1000)
                        // Configuración de las etiquetas
                        setEntryLabelTextSize(10f)
                        setEntryLabelColor(android.graphics.Color.BLACK)
                        // Habilitar interacción
                        isHighlightPerTapEnabled = true
                        setTouchEnabled(true)
                    }
                },
                update = { chart ->
                    val entries = listaMenosVistas.map { (id, vistas) ->
                        PieEntry(
                            vistas.toFloat(),
                            id // El ID se mostrará como etiqueta
                        )
                    }
                    // Definir colores para el gráfico
                    val colors = listOf(
                        android.graphics.Color.parseColor("#FF9AA2"), // Rosa melocotón
                        android.graphics.Color.parseColor("#ACE7FF"),  // Azul brillante
                        android.graphics.Color.parseColor("#C7CEEA"), // Azul bebé
                        android.graphics.Color.parseColor("#FFDAC1"), // Durazno claro
                        android.graphics.Color.parseColor("#B5EAD7"), // Verde menta
                        android.graphics.Color.parseColor("#DCD3FF"), // Lila claro
                        android.graphics.Color.parseColor("#FFB7B2"), // Rosa coral
                        android.graphics.Color.parseColor("#B5D8EB"), // Azul cielo claro
                        android.graphics.Color.parseColor("#F8DFD4"), // Rosa piel
                        android.graphics.Color.parseColor("#E2F0CB") // Verde lima suave

                    )
                    val dataSet = PieDataSet(entries, "").apply {
                        setColors(colors)
                        valueTextSize = 12f
                        sliceSpace = 2f

                        // Formatear valores sin decimales
                        valueFormatter = object : ValueFormatter() {
                            override fun getFormattedValue(value: Float): String {
                                return "${value.toInt()}"
                            }
                        }
                    }
                    val pieData = PieData(dataSet)
                    chart.data = pieData
                }
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        showDialog = true
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info"
                    )
                }
            }
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp),  // Añade espacio arriba del divider
            color = Color.Gray,    // Color opcional
            thickness = 1.dp            // Grosor opcional
        )
    }
    if (showDialog) {
        DialogInfo("Motos Menos Vistas", motosMenosVistasInfo)
    }
}