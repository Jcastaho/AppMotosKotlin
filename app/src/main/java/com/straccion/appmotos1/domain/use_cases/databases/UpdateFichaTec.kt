package com.straccion.appmotos1.domain.use_cases.databases

import com.straccion.appmotos1.domain.repository.DataBaseRepository
import javax.inject.Inject

class UpdateFichaTec @Inject constructor(private val repository: DataBaseRepository) {
    suspend operator fun invoke(fichaItems: Map<String, Any>, motoId: String) = repository.update(fichaItems, motoId)
}