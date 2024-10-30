package com.straccion.appmotos1.domain.repository

import com.straccion.appmotos1.domain.model.FavoritasUsuarios
import com.straccion.appmotos1.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface FavoritasRepository {
    suspend fun getMotosFavoritas(uid: String): Flow<Response<List<FavoritasUsuarios>>>
    suspend fun eliminarMotosFav(id: List<String>, userId: String): Response<Boolean>
    suspend fun agregarMotosFav(id: String, userId: String): Response<Boolean>
}