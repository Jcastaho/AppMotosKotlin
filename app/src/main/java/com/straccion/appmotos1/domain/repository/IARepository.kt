package com.straccion.appmotos1.domain.repository


interface IARepository {
    suspend fun sendPrompt(prompt: String): String
}