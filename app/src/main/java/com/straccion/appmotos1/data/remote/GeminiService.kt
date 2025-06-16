package com.straccion.appmotos1.data.remote

import com.google.ai.client.generativeai.GenerativeModel


class GeminiService(private val generativeModel: GenerativeModel) {
    suspend fun getAIResponse(prompt: String): String {
        return try {
            val chat = generativeModel.startChat() // Inicia el chat aunque no lo uses como tal
            val response = chat.sendMessage(prompt) //
            response.text.toString()
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}