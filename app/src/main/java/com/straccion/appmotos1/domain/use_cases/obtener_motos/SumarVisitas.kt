package com.straccion.appmotos1.domain.use_cases.obtener_motos

import com.straccion.appmotos1.domain.repository.MotosRepository
import javax.inject.Inject

class SumarVisitas @Inject constructor(private val repository: MotosRepository) {
    suspend operator fun invoke(idMoto: String, busqueda: Boolean) = repository.sumarVisitas(idMoto, busqueda)
}