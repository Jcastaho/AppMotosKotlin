package com.straccion.appmotos1.domain.use_cases.auth

import com.straccion.appmotos1.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUser @Inject constructor( private val repository: AuthRepository) {
    operator fun invoke() = repository.currentUser
}