package com.straccion.appmotos1.domain.use_cases.favoritos

import com.straccion.appmotos1.domain.model.FavoritasUsuarios
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.repository.FavoritasRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtenerMotosFavoritas @Inject constructor(private val repository: FavoritasRepository) {
    suspend operator fun invoke(uid: String): Flow<Response<List<FavoritasUsuarios>>> = repository.getMotosFavoritas(uid)
}