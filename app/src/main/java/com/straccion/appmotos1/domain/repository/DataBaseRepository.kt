package com.straccion.appmotos1.domain.repository

import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.MotoDiferencias
import com.straccion.appmotos1.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface DataBaseRepository {
    suspend fun update(fichaItems: Map<String, Any>, motoId: String):Response<Boolean>
    suspend fun ocultarMotocicleta(visible: Boolean, motoId: String):Response<Boolean>
    suspend fun delete(motoId: String): Response<Boolean>
    suspend fun getAllMotos(): Flow<Response<List<CategoriaMotos>>>
    suspend fun actualizarMotos(): Response<String>
    suspend fun ActualizacionesRealizadas(): List<MotoDiferencias>
}