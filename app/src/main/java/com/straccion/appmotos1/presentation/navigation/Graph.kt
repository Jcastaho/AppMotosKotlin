package com.straccion.appmotos1.presentation.navigation

object Graph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"//inicio
    const val BASE_DATOS = "basedatos_graph"
    const val COMPARAR_MOTOS = "comparar_graph"
    const val MI_MOTO_IDEAL = "mi_moto_ideal_graph"
    const val FAVORITAS = "favoritas_graph"
    const val ESTADISTICA = "estadistica_graph"

    // Subgrafos y pantallas compartidas
    const val AGREGAR_REGISTRO = "agregar_registro_graph"
    const val DETALLES_MOTO = "detalles_moto?moto={moto}&busqueda={busqueda}" // Pantalla compartida
}