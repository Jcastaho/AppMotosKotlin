package com.straccion.appmotos1.domain.use_cases.obtener_motos

import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.repository.MotosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtenerMotos @Inject constructor(private val repository: MotosRepository) {
    suspend operator fun invoke(): Flow<Response<List<CategoriaMotos>>> = repository.getMotos()
}