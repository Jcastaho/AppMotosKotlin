package com.straccion.appmotos1.domain.use_cases.auth

import com.straccion.appmotos1.domain.repository.AuthRepository
import javax.inject.Inject

class Login @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke() =  repository.login()
}