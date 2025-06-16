package com.straccion.appmotos1.presentation.screens.vistafavoritas.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

@SuppressLint("SuspiciousIndentation")
suspend fun CompartirImagenes(
    context: Context,
    motosSeleccionadas: Response<List<CategoriaMotos>>
) {
    withContext(Dispatchers.Default) {
        val motos = when (motosSeleccionadas) {
            is Response.Success -> motosSeleccionadas.data
            else ->{
                Log.w("Compartir", "No hay motos seleccionadas para compartir")
                emptyList()
            }
        }
        val imageUrls = motos.flatMap { it.imagenesPrincipales }
        Log.d("Compartir", "URLs de imágenes: $imageUrls")

        val uriArray: ArrayList<Uri> = ArrayList()

        imageUrls.forEachIndexed { index, urlString ->
            try {
                val file = withContext(Dispatchers.IO) {
                    val tempFile = File(context.cacheDir, "shared_image_$index.jpg")
                    URL(urlString).openStream().use { input ->
                        FileOutputStream(tempFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                    tempFile
                }

                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    file
                )
                uriArray.add(uri)
            } catch (e: Exception) {
                Log.e("Compartir", "Error al procesar imagen: $urlString", e)
            }
        }

        if (uriArray.isNotEmpty()) {
            withContext(Dispatchers.Main) {
                val shareIntent = Intent().apply {
                    action = if (uriArray.size > 1) Intent.ACTION_SEND_MULTIPLE else Intent.ACTION_SEND
                    // Añadir las imágenes
                    if (uriArray.size > 1) {
                        putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriArray)
                        type = "image/*"  // Tipo de datos para múltiples imágenes
                    } else {
                        putExtra(Intent.EXTRA_STREAM, uriArray[0])
                        type = "image/*"  // Tipo de datos para una sola imagen
                    }

                    // Permitir el acceso a las imágenes compartidas
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    if (motos.size > 1) {
                        "¡Mira estas ${motos.size} increíbles motos que encontré!"
                    } else {
                        "¡Mira esta increíble moto que encontré! ${motos.first().id}"
                    }
                )

                try {
                    val chooserIntent = Intent.createChooser(shareIntent, "Compartir motos")
                        context.startActivity(chooserIntent)
                } catch (e: Exception) {
                    Log.e("Compartir", "Error al iniciar actividad para compartir", e)
                }
            }
        } else {
            Log.w("Compartir", "No hay imágenes para compartir")
        }
    }
}

