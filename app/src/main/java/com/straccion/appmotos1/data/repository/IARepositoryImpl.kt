package com.straccion.appmotos1.data.repository

import com.straccion.appmotos1.data.remote.GeminiService
import com.straccion.appmotos1.domain.repository.IARepository
import javax.inject.Inject

class IARepositoryImpl @Inject constructor(private val service: GeminiService) : IARepository {
    override suspend fun sendPrompt(prompt: String): String {
        return service.getAIResponse(prompt)
    }
}
