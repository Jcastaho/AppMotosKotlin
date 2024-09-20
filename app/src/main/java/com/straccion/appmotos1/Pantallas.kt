package com.straccion.appmotos1

sealed class Pantallas (val route: String){
    data object PantallaPrincipal : Pantallas("pantalla_principal")
    data object PantallaDetallesMoto : Pantallas("moto_detail/{motoId}"){
        fun createRoute(motoID: String) = "moto_detail/$motoID"
    }
    data object BasedeDatos : Pantallas("base_datos")
    data object AggRegistro : Pantallas("agg_registro")
    data object ModRegistro : Pantallas("mod_registro")
    data object EditarRegistro : Pantallas("editar_registro/{motoId}"){
        fun Route(motoID: String) = "editar_registro/$motoID"
    }
    data object ElimRegistro : Pantallas("elim_registro")
    data object CompararMotos : Pantallas("comparar_motos")
    data object VistaPreguntas : Pantallas("vista_preguntas")
    data object Favoritas : Pantallas("motos_favoritas/{motoId}"){
        fun createRouteFav(motoID: String) = "moto_fav/$motoID"
    }
    data object Estadisticas : Pantallas("estadisticas")
}