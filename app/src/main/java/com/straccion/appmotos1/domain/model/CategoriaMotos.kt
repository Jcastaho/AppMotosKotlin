package com.straccion.appmotos1.domain.model

data class CategoriaMotos(
    var id: String = "",
    var ubicacionImagenes: Map<String, String> = mapOf(),
    var modelos: Map<String, String> = mapOf(),
    var fichaTecnica: Map<String, Any> = mapOf(),
    var imagenesPrincipales: List<String> = listOf(),
    var imagenesColores1: List<String> = listOf(),
    var imagenesColores2: List<String> = listOf(),
    var imagenesColores3: List<String> = listOf(),
    var imagenesColores4: List<String> = listOf(),
    var imagenesColores5: List<String> = listOf(),
    var imagenesColores6: List<String> = listOf(),
    var caracteristicasImagenes: List<String> = listOf(),
    var caracteristicasTexto: List<String> = listOf(),
    var colores: List<String> = listOf(),
    var marcaMoto: String = "",
    var descripcion: String = "",
    val consumoPorGalon: Int = 0,
    val prioridad: Int = 0,
    val velocidadMaxima: Int = 0,
    val diferenciaValor: Int = 0,
    val precioActual: Int = 0,
    val precioAnterior: Int = 0,
    val visible: Boolean = true,
    var descuento: Boolean = false,
    var favoritos: Boolean = false,
    var listaFavoritos: List<String> = listOf(),
    var uidUser: String = ""

)

