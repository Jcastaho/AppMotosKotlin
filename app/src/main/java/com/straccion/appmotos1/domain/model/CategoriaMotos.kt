package com.straccion.appmotos1.domain.model

import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
) {
    fun toJson(): String = Gson().toJson(
        CategoriaMotos(
            id,
            ubicacionImagenes,
            modelos,
            fichaTecnica,
            imagenesPrincipales.map { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) },
            imagenesColores1.map { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) },
            imagenesColores2.map { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) },
            imagenesColores3.map { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) },
            imagenesColores4.map { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) },
            imagenesColores5.map { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) },
            imagenesColores6.map { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) },
            caracteristicasImagenes.map { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) },
            caracteristicasTexto,
            colores,
            marcaMoto,
            descripcion,
            consumoPorGalon,
            prioridad,
            velocidadMaxima,
            diferenciaValor,
            precioActual,
            precioAnterior,
            visible,
            descuento,
            favoritos,
            listaFavoritos,
            uidUser
        )
    )
    companion object{
        fun fromJson(data: String): CategoriaMotos = Gson().fromJson(data, CategoriaMotos::class.java)
    }
}


