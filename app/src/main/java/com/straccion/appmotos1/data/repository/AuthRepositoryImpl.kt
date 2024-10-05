package com.straccion.appmotos1.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth): AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(): Response<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInAnonymously().await()
            Response.Success(result.user!!)
        } catch (e: Exception){
            e.printStackTrace()
            Response.Failure(e)
        }
    }

}