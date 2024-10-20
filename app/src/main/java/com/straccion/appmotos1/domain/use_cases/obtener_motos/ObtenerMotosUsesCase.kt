package com.straccion.appmotos1.domain.use_cases.obtener_motos

data class ObtenerMotosUsesCase(
    val obtenerMotosVisibles: ObtenerMotosVisibles,
    val obtenerMotosById: ObtenerMotosById
)