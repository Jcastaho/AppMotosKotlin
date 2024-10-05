package com.straccion.appmotos1.domain.use_cases.auth

data class AuthUsesCases (
    val getCurrentUser: GetCurrentUser,
    val login: Login
)