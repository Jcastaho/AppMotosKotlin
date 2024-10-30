package com.straccion.appmotos1.domain.use_cases.favoritos

import com.straccion.appmotos1.domain.repository.FavoritasRepository
import javax.inject.Inject

class AgregarMotoFav @Inject constructor(private val repository: FavoritasRepository) {
    suspend operator fun invoke(id: String, userId: String) = repository.agregarMotosFav(id, userId)
}