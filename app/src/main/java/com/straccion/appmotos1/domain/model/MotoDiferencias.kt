package com.straccion.appmotos1.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class MotoDiferencias (
    val id: String,
    val differences: Map<String, Difference>,
    val status: String? = null
)

@Serializable
data class Difference(
    val webscraping: JsonElement,
    val data: String
)