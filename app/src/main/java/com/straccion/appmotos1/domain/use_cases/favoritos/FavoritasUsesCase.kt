package com.straccion.appmotos1.domain.use_cases.favoritos

data class  FavoritasUsesCase (
    val obtenerMotosFavoritas: ObtenerMotosFavoritas,
    val quitarMotosFav: QuitarMotosFav,
    val agregarMotoFav: AgregarMotoFav
)