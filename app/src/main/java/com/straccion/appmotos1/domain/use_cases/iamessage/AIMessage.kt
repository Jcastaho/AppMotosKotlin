package com.straccion.appmotos1.domain.use_cases.iamessage

import com.straccion.appmotos1.domain.repository.IARepository
import javax.inject.Inject

class AIMessage @Inject constructor(
    private val repository: IARepository
) {
    suspend operator fun invoke(prompt: String): String {
        return repository.sendPrompt(prompt)
    }
}