package com.straccion.appmotos1.domain.repository

import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.FavoritasUsuarios
import com.straccion.appmotos1.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface MotosRepository {
    suspend fun getMotosVisibles(): Flow<Response<List<CategoriaMotos>>>
    suspend fun getMotosById(idMoto: String): Flow<Response<List<CategoriaMotos>>>
    suspend fun sumarVisitas(idMoto: String, busqueda: Boolean): Response<Boolean>
}