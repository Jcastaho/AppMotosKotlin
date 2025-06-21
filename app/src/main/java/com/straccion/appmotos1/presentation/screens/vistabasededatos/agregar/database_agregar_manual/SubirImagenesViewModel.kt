package com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar_manual

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

// Estado para la pantalla de subida de imágenes
data class ImageUploadState(
    val imagenesPrincipales: List<Uri> = listOf(),
    val caracteristicasImagenes: List<Uri> = listOf(),
    val imagenesPorColor: Map<String, List<Uri>> = mapOf(),
    val isUploading: Boolean = false
)

@HiltViewModel
class SubirImagenesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle // Para recibir los argumentos iniciales
) : ViewModel() {

    private val _uiState = MutableStateFlow(ImageUploadState())
    val uiState = _uiState.asStateFlow()

    // Así recibes cada dato usando la clave que definiste en la ruta
    val nombreMoto: String = savedStateHandle.get<String>("nombreMoto") ?: ""
    val categoria: String = savedStateHandle.get<String>("categoria") ?: ""
    val marca: String = savedStateHandle.get<String>("marca") ?: ""
    val fabricante: String = savedStateHandle.get<String>("fabricante") ?: ""

    fun addImage(section: String, uri: Uri, color: String? = null) {
        _uiState.update { currentState ->
            when (section) {
                "principales" -> currentState.copy(imagenesPrincipales = currentState.imagenesPrincipales + uri)
                "caracteristicas" -> currentState.copy(caracteristicasImagenes = currentState.caracteristicasImagenes + uri)
                "color" -> {
                    if (color != null) {
                        val newMap = currentState.imagenesPorColor.toMutableMap()
                        val currentList = newMap[color] ?: listOf()
                        newMap[color] = currentList + uri
                        currentState.copy(imagenesPorColor = newMap)
                    } else {
                        currentState
                    }
                }
                else -> currentState
            }
        }
    }

    fun removeImage(section: String, uri: Uri, color: String? = null) {
        _uiState.update { currentState ->
            when (section) {
                "principales" -> currentState.copy(imagenesPrincipales = currentState.imagenesPrincipales - uri)
                "caracteristicas" -> currentState.copy(caracteristicasImagenes = currentState.caracteristicasImagenes - uri)
                "color" -> {
                    if (color != null) {
                        val newMap = currentState.imagenesPorColor.toMutableMap()
                        val currentList = newMap[color] ?: listOf()
                        newMap[color] = currentList - uri
                        currentState.copy(imagenesPorColor = newMap)
                    } else {
                        currentState
                    }
                }
                else -> currentState
            }
        }
    }

    fun addColor(color: String) {
        if (color.isBlank() || _uiState.value.imagenesPorColor.containsKey(color)) return
        _uiState.update { currentState ->
            val newMap = currentState.imagenesPorColor.toMutableMap()
            newMap[color] = emptyList()
            currentState.copy(imagenesPorColor = newMap)
        }
    }

    fun removeColor(color: String) {
        _uiState.update { currentState ->
            val newMap = currentState.imagenesPorColor.toMutableMap()
            newMap.remove(color)
            currentState.copy(imagenesPorColor = newMap)
        }
    }

    /**
     * Esta es la función clave. En el futuro, aquí irá la lógica de Firebase Storage.
     * 1. Sube cada URI de la UI a Storage.
     * 2. Recopila las URLs de descarga que devuelve Storage.
     * 3. Navega a la siguiente pantalla pasando las URLs.
     */
    fun uploadImagesAndContinue(navController: NavHostController) {
        viewModelScope.launch {
            _uiState.update { it.copy(isUploading = true) }

            // --- SIMULACIÓN DE SUBIDA A FIREBASE STORAGE ---
            // En el futuro, esta sección será reemplazada por llamadas reales a Storage.
            // Por cada `uri` en `_uiState.value`, llamarías a `storageRef.putFile(uri).await()`
            // y luego `storageRef.downloadUrl.await().toString()`
            val urlsPrincipales = _uiState.value.imagenesPrincipales.map { "https://firebasestorage.googleapis.com/v0/b/bucket/o/principales%2F${it.lastPathSegment}.jpg" }
            val urlsCaracteristicas = _uiState.value.caracteristicasImagenes.map { "https://firebasestorage.googleapis.com/v0/b/bucket/o/caracteristicas%2F${it.lastPathSegment}.jpg" }

            val urlsPorColor = _uiState.value.imagenesPorColor.mapValues { (color, uris) ->
                uris.map { "https://firebasestorage.googleapis.com/v0/b/bucket/o%2F${color}%2F${it.lastPathSegment}.jpg" }
            }
            // --- FIN DE LA SIMULACIÓN ---

            _uiState.update { it.copy(isUploading = false) }

            // Navegar a la pantalla de formulario manual, pasando las URLs obtenidas.
            // Serializamos las listas y mapas a JSON para pasarlos como argumentos de navegación.
            val gson = Gson()
            val principalesJson = URLEncoder.encode(gson.toJson(urlsPrincipales), StandardCharsets.UTF_8.toString())
            val caracteristicasJson = URLEncoder.encode(gson.toJson(urlsCaracteristicas), StandardCharsets.UTF_8.toString())
            val coloresJson = URLEncoder.encode(gson.toJson(urlsPorColor.keys.toList()), StandardCharsets.UTF_8.toString())

            // Aquí separamos las urls por color en argumentos distintos para evitar sobrepasar el límite de tamaño de los argumentos
            val navRoute = StringBuilder("registrar_moto_manual_form?")
            navRoute.append("nombreMoto=$nombreMoto&")
            navRoute.append("principales=$principalesJson&")
            navRoute.append("caracteristicas=$caracteristicasJson&")
            navRoute.append("colores=$coloresJson")

            urlsPorColor.onEachIndexed { index, entry ->
                val colorUrlsJson = URLEncoder.encode(gson.toJson(entry.value), StandardCharsets.UTF_8.toString())
                navRoute.append("&imagenesColores${index+1}=$colorUrlsJson")
            }

            navController.navigate(navRoute.toString())
        }
    }
}