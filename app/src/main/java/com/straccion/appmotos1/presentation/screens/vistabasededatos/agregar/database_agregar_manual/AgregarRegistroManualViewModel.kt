package com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar_manual

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.use_cases.databases.DataBasesUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgregarRegistroManualViewModel @Inject constructor(
    private val dataBasesUsesCase: DataBasesUsesCase
) : ViewModel() {
    private val _formState = MutableStateFlow(MotoFormState())
    val formState = _formState.asStateFlow()

    // --- Funciones para actualizar el estado del formulario ---

    fun onFieldChange(field: String, value: String) {
        _formState.update { currentState ->
            when (field) {
                "marcaMoto" -> currentState.copy(marcaMoto = value)
                "descripcion" -> currentState.copy(descripcion = value)
                "consumoPorGalon" -> currentState.copy(consumoPorGalon = value.filter { it.isDigit() })
                "prioridad" -> currentState.copy(prioridad = value.filter { it.isDigit() })
                "velocidadMaxima" -> currentState.copy(velocidadMaxima = value.filter { it.isDigit() })
                "precioActual" -> currentState.copy(precioActual = value.filter { it.isDigit() })
                "precioAnterior" -> currentState.copy(precioAnterior = value.filter { it.isDigit() })
                else -> currentState
            }
        }
    }

    fun onBooleanChange(field: String, value: Boolean) {
        _formState.update { currentState ->
            when (field) {
                "visible" -> currentState.copy(visible = value)
                "descuento" -> currentState.copy(descuento = value)
                else -> currentState
            }
        }
    }

    // --- Funciones para listas dinámicas ---
    fun addToList(listName: String) {
        _formState.value.let {
            when(listName) {
                "imagenesPrincipales" -> it.imagenesPrincipales.add("")
                "imagenesColores1" -> it.imagenesColores1.add("")
                "caracteristicasImagenes" -> it.caracteristicasImagenes.add("")
                "caracteristicasTexto" -> it.caracteristicasTexto.add("")
                "colores" -> it.colores.add("")
            }
        }
    }

    fun updateListItem(listName: String, index: Int, value: String) {
        _formState.value.let {
            when(listName) {
                "imagenesPrincipales" -> it.imagenesPrincipales[index] = value
                "imagenesColores1" -> it.imagenesColores1[index] = value
                "caracteristicasImagenes" -> it.caracteristicasImagenes[index] = value
                "caracteristicasTexto" -> it.caracteristicasTexto[index] = value
                "colores" -> it.colores[index] = value
            }
        }
    }

    fun removeFromList(listName: String, index: Int) {
        _formState.value.let {
            when(listName) {
                "imagenesPrincipales" -> it.imagenesPrincipales.removeAt(index)
                "imagenesColores1" -> it.imagenesColores1.removeAt(index)
                "caracteristicasImagenes" -> it.caracteristicasImagenes.removeAt(index)
                "caracteristicasTexto" -> it.caracteristicasTexto.removeAt(index)
                "colores" -> it.colores.removeAt(index)
            }
        }
    }

    // --- Funciones para Mapas dinámicos ---
    fun addToMap(mapName: String, key: String, value: String) {
        if (key.isBlank()) return
        _formState.value.let {
            when(mapName) {
                "modelos" -> it.modelos[key] = value
                "fichaTecnica" -> it.fichaTecnica[key] = value
            }
        }
    }

    fun removeFromMap(mapName: String, key: String) {
        _formState.value.let {
            when(mapName) {
                "modelos" -> it.modelos.remove(key)
                "fichaTecnica" -> it.fichaTecnica.remove(key)
            }
        }
    }

    // --- Lógica para guardar ---
    fun saveRegistro() {
        viewModelScope.launch {
            // Aquí recibes el id y ubicacionImagenes que mencionaste
            val idGenerado = "some-generated-id"
            val ubicacionImagenesObtenida = mapOf("key" to "value")

            // 1. Convierte el estado del formulario al data class CategoriaMotos
            val nuevoRegistro = formState.value.toCategoriaMotos(idGenerado, ubicacionImagenesObtenida)

            // 2. Llama al UseCase para guardar en la base de datos
            // try {
            //     dataBasesUsesCase.agregarRegistro(nuevoRegistro)
            //     // Navegar hacia atrás o mostrar un mensaje de éxito
            // } catch (e: Exception) {
            //     // Mostrar error
            // }
            println("Guardando: $nuevoRegistro")
        }
    }

    private fun MotoFormState.toCategoriaMotos(id: String, ubicacionImagenes: Map<String, String>): CategoriaMotos {
        return CategoriaMotos(
            id = id,
            ubicacionImagenes = ubicacionImagenes,
            modelos = this.modelos,
            // Nota: Ficha tecnica se guarda como Map<String, String> por simplicidad.
            // Para guardar como Map<String, Any> se necesitaría una lógica más compleja para convertir los tipos.
            fichaTecnica = this.fichaTecnica,
            imagenesPrincipales = this.imagenesPrincipales.filter { it.isNotBlank() },
            imagenesColores1 = this.imagenesColores1.filter { it.isNotBlank() },
            // ... resto de listas de imágenes
            caracteristicasImagenes = this.caracteristicasImagenes.filter { it.isNotBlank() },
            caracteristicasTexto = this.caracteristicasTexto.filter { it.isNotBlank() },
            colores = this.colores.filter { it.isNotBlank() },
            marcaMoto = this.marcaMoto,
            descripcion = this.descripcion,
            consumoPorGalon = this.consumoPorGalon.toIntOrNull() ?: 0,
            prioridad = this.prioridad.toIntOrNull() ?: 0,
            velocidadMaxima = this.velocidadMaxima.toIntOrNull() ?: 0,
            precioActual = this.precioActual.toIntOrNull() ?: 0,
            precioAnterior = this.precioAnterior.toIntOrNull() ?: 0,
            diferenciaValor = (this.precioAnterior.toIntOrNull() ?: 0) - (this.precioActual.toIntOrNull() ?: 0),
            visible = this.visible,
            descuento = this.descuento,
            // ... el resto de campos que tienen valores por defecto o no están en el formulario
        )
    }

}

data class MotoFormState(
    // Campos de texto simples
    val marcaMoto: String = "",
    val descripcion: String = "",
    val consumoPorGalon: String = "0",
    val prioridad: String = "0",
    val velocidadMaxima: String = "0",
    val precioActual: String = "0",
    val precioAnterior: String = "0",

    // Booleans
    val visible: Boolean = true,
    val descuento: Boolean = false,

    // Listas dinámicas
    val modelos: MutableMap<String, String> = mutableStateMapOf(),
    val fichaTecnica: MutableMap<String, String> = mutableStateMapOf(), // Simplificado a String-String por simplicidad en la UI
    val imagenesPrincipales: MutableList<String> = mutableStateListOf(""),
    val imagenesColores1: MutableList<String> = mutableStateListOf(""),
    // ... puedes añadir el resto de imagenesColoresX de la misma forma
    val caracteristicasImagenes: MutableList<String> = mutableStateListOf(""),
    val caracteristicasTexto: MutableList<String> = mutableStateListOf(""),
    val colores: MutableList<String> = mutableStateListOf(""),
)
