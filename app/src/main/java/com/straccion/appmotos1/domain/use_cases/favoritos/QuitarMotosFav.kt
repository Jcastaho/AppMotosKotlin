package com.straccion.appmotos1.domain.use_cases.favoritos

import com.straccion.appmotos1.domain.repository.FavoritasRepository
import javax.inject.Inject

class QuitarMotosFav @Inject constructor(private val repository: FavoritasRepository) {
    suspend operator fun invoke(id: List<String>, userId: String) = repository.eliminarMotosFav(id, userId)
}