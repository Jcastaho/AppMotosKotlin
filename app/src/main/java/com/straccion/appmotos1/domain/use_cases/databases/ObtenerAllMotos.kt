package com.straccion.appmotos1.domain.use_cases.databases

import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.repository.DataBaseRepository
import com.straccion.appmotos1.domain.repository.MotosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtenerAllMotos @Inject constructor(private val repository: DataBaseRepository) {
    suspend operator fun invoke(): Flow<Response<List<CategoriaMotos>>> = repository.getAllMotos()
}