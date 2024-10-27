package com.straccion.appmotos1.domain.use_cases.databases

import com.straccion.appmotos1.domain.repository.DataBaseRepository
import javax.inject.Inject

class EliminarMoto @Inject constructor(private val repository: DataBaseRepository) {
    suspend operator fun invoke(motoId: String) = repository.delete(motoId)
}