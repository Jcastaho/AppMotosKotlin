package com.straccion.appmotos1.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RespuestaGemini(
    val lista1: List<String>,
    val lista2: List<String>
)