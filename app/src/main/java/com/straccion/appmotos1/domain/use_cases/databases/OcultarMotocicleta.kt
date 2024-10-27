package com.straccion.appmotos1.domain.use_cases.databases

import com.straccion.appmotos1.domain.repository.DataBaseRepository
import javax.inject.Inject

class OcultarMotocicleta @Inject constructor(private val repository: DataBaseRepository) {
    suspend operator fun invoke(visible: Boolean, motoId: String) = repository.ocultarMotocicleta(visible, motoId)
}