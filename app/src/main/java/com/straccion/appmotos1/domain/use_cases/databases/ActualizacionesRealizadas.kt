package com.straccion.appmotos1.domain.use_cases.databases

import com.straccion.appmotos1.domain.model.MotoDiferencias
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.repository.DataBaseRepository
import javax.inject.Inject

class ActualizacionesRealizadas @Inject constructor(private val repository: DataBaseRepository) {
    suspend operator fun invoke():List<MotoDiferencias> = repository.ActualizacionesRealizadas()
}