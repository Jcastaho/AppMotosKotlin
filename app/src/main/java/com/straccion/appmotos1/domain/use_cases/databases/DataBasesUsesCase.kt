package com.straccion.appmotos1.domain.use_cases.databases

data class DataBasesUsesCase (
    val updateFichaTec: UpdateFichaTec,
    val ocultarMotocicleta: OcultarMotocicleta,
    val eliminarMoto: EliminarMoto,
    val obtenerAllMotos: ObtenerAllMotos,
    val actualizarMotos: ActualizarMotos,
    val actualizacionesRealizadas: ActualizacionesRealizadas,
)