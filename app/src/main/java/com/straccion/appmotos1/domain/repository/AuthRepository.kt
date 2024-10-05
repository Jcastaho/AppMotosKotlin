package com.straccion.appmotos1.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.straccion.appmotos1.domain.model.Response

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(): Response<FirebaseUser>
}