package com.straccion.appmotos1.presentation.navigation.screen.base_de_datos

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class NavAgregarRegistroScreen(val route: String) {
    data object AgregarRegistro: NavAgregarRegistroScreen("agregar_registro")

    // Ruta para la pantalla de subir imágenes.
    // Necesita {nombreMoto} y {categoria} como argumentos obligatorios.
    data object SubirImagenes: NavAgregarRegistroScreen("subir_imagenes_screen/{nombreMoto}/{categoria}/{marca}/{fabricante}") {
        // Función de ayuda para construir la ruta con los valores reales
        fun createRoute(nombreMoto: String, categoria: String, marca: String, fabricante: String): String {
            val nombreEncoded = URLEncoder.encode(nombreMoto, StandardCharsets.UTF_8.toString())
            val categoriaEncoded = URLEncoder.encode(categoria, StandardCharsets.UTF_8.toString())
            val marcaEncoded = URLEncoder.encode(marca, StandardCharsets.UTF_8.toString())
            val fabricanteEncoded = URLEncoder.encode(fabricante, StandardCharsets.UTF_8.toString())
            return "subir_imagenes_screen/$nombreEncoded/$categoriaEncoded/$marcaEncoded/$fabricanteEncoded"
        }
    }
    // Ruta para la pantalla del formulario final.
    // Usa parámetros de consulta (?) porque son muchos y algunos opcionales.
    data object FormularioFinal: NavAgregarRegistroScreen(
        "registrar_moto_manual_form?nombreMoto={nombreMoto}&principales={principales}&caracteristicas={caracteristicas}&colores={colores}&imagenesColores1={imagenesColores1}&imagenesColores2={imagenesColores2}&imagenesColores3={imagenesColores3}&imagenesColores4={imagenesColores4}&imagenesColores5={imagenesColores5}&imagenesColores6={imagenesColores6}"
    )

  //  data object RegistrarMotoManual: NavAgregarRegistroScreen("registrar_motomanual")
    data object RegistrarMotoWebScrapping: NavAgregarRegistroScreen("registrar_moto_webscrapping")
}