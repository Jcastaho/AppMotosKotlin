package com.straccion.appmotos1.presentation.screens.vistaestadistica.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.straccion.appmotos1.presentation.screens.vistaestadistica.EstadisticaViewModel

@Composable
fun GraficoMotosMasVistas(
    listaMasVistas: List<Pair<String, Int>>,
    viewModel: EstadisticaViewModel = hiltViewModel()
) {
    var showDialog by viewModel.mostrarDialog1
    val listaMasVistasInfo by viewModel.motosMasVistasInfo.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 15.dp),
            text = "Motos Mas Vistas",
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
                    val entries = listaMasVistas.map { (id, vistas) ->
                        PieEntry(
                            vistas.toFloat(),
                            id // El ID se mostrará como etiqueta
                        )
                    }
                    // Definir colores para el gráfico
                    val colors = listOf(
                        android.graphics.Color.parseColor("#64B5F6"), // Azul
                        android.graphics.Color.parseColor("#EC407A"), // Rosa
                        android.graphics.Color.parseColor("#FFA726"), // Naranja
                        android.graphics.Color.parseColor("#66BB6A"), // Verde
                        android.graphics.Color.parseColor("#E0E0E0"), // Gris
                        android.graphics.Color.parseColor("#BA68C8"), // Púrpura
                        android.graphics.Color.parseColor("#4DB6AC"), // Turquesa
                        android.graphics.Color.parseColor("#FFB74D"), // Naranja claro
                        android.graphics.Color.parseColor("#9575CD"), // Violeta
                        android.graphics.Color.parseColor("#4FC3F7")  // Azul claro
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
                            color = Color.White,
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
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp),  // Añade espacio arriba del divider
            color = Color.Gray,    // Color opcional
            thickness = 1.dp            // Grosor opcional
        )
    }
    if (showDialog) {
       DialogInfo("Motos Mas Vistas", listaMasVistasInfo)
    }
}