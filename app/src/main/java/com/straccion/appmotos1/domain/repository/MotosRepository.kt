package com.straccion.appmotos1.domain.repository

import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface MotosRepository {
    suspend fun getMotos(): Flow<Response<List<CategoriaMotos>>>
}