package com.straccion.appmotos1

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions


fun authenticateUser(onSuccess: () -> Unit) {
    val auth = FirebaseAuth.getInstance()
    if (auth.currentUser == null) {
        // Si no hay usuario autenticado, autenticarse anónimamente
        auth.signInAnonymously().addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess() // Ejecutar la lógica que sigue solo después de la autenticación exitosa
            } else {
                Log.e("AuthError", "Error en la autenticación anónima", it.exception)
            }
        }
    } else {
        // Si ya hay un usuario autenticado, ejecutar la lógica de éxito de inmediato
        onSuccess()
    }
}

fun getMotos(onMotosCambiadas: (List<CategoriaMotos>) -> Unit) {
    authenticateUser {
        attachSnapshotListener(onMotosCambiadas)
    }
}


fun getMotosFav(onMotosCambiadas: (List<String>, String?) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val currentUser = auth.currentUser

    if (currentUser != null) {
        val userUid = currentUser.uid
        db.collection("FavoritasUsuarios")
            .whereEqualTo("uid", userUid)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("getMotosFavoritasUsuariosEnTiempoReal", "Listen failed.", e)
                    onMotosCambiadas(emptyList(), userUid)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val motoIds = snapshot.documents.flatMap { doc ->
                        (doc.get("motoId") as? List<*>)?.filterIsInstance<String>() ?: emptyList()
                    }.distinct()
                    onMotosCambiadas(motoIds, userUid)
                } else {
                    Log.d("getMotosEnTiempoReal", "No data found for user: $userUid")
                    onMotosCambiadas(emptyList(), userUid)
                }
            }
    } else {
        Log.d("getMotosEnTiempoReal", "No user is authenticated")
        onMotosCambiadas(emptyList(), null)
    }
}

private fun attachSnapshotListener(onMotosCambiadas: (List<CategoriaMotos>) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("NuevaMotos").addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w("getMotosEnTiempoReal", "Listen failed.", e)
            return@addSnapshotListener
        }
        if (snapshot != null && !snapshot.isEmpty) {
            val motos = snapshot.documents.mapNotNull { doc ->
                doc.toObject(CategoriaMotos::class.java)?.apply {
                    id = doc.getString("id") ?: doc.id
                    marcaMoto = ubicacionImagenes["carpeta1"] ?: ""
                }
            }
            onMotosCambiadas(motos)
        } else {
            Log.d("getMotosEnTiempoReal", "No data found")
        }
    }
}


suspend fun actualizarFichaTecnicayOtrosDatosEnFirebase(
    motoId: String,
    nuevaFichaTecnica: Map<String, Any>
): Boolean {
    val auth = FirebaseAuth.getInstance()
    if (auth.currentUser == null) {
        auth.signInAnonymously().await()
    }

    val db = FirebaseFirestore.getInstance()
    return try {
        // Primero, busca el documento con el campo 'id' igual a motoId
        val querySnapshot = db.collection("NuevaMotos")
            .whereEqualTo("id", motoId)
            .get()
            .await()

        if (querySnapshot.isEmpty) {
            Log.e("actualizarFichaTecnica", "No se encontró ningún documento con el id $motoId")
            return false
        }

        val otrosDatos: List<String> = listOf(
            "velocidadMaxima",
            "prioridad",
            "precioActual",
            "precioAnterior",
            "diferenciaValor",
            "descuento",
            "consumoPorGalon",
        )

        // Supone que el 'id' es único, así que debería haber solo un documento
        val documentSnapshot = querySnapshot.documents[0]
        val documentId = documentSnapshot.id

        // Separar los datos de fichaTecnica y los otros datos
        val fichaTecnicaActualizada = nuevaFichaTecnica.filterKeys { it !in otrosDatos }
        val otrosDatosActualizados = nuevaFichaTecnica.filterKeys { it in otrosDatos }


        // Crear un mapa para la actualización
        val actualizaciones = mutableMapOf<String, Any>()
        actualizaciones["fichaTecnica"] = fichaTecnicaActualizada
        actualizaciones.putAll(otrosDatosActualizados)

        // Ahora puedes actualizar este documento usando su ID
        db.collection("NuevaMotos").document(documentId)
            .update(actualizaciones)
            .await()


        true
    } catch (e: Exception) {
        Log.e("actualizarFichaTecnica", "Error updating ficha tecnica", e)
        false
    }
}

suspend fun actualizarVisibilidadEnFirebase(motoId: String, nuevoEstadoVisible: Boolean): Boolean {
    val auth = FirebaseAuth.getInstance()
    if (auth.currentUser == null) {
        auth.signInAnonymously().await()
    }

    val db = FirebaseFirestore.getInstance()
    return try {
        // Busca el documento con el campo 'id' igual a motoId
        val querySnapshot = db.collection("NuevaMotos")
            .whereEqualTo("id", motoId)
            .get()
            .await()

        if (querySnapshot.isEmpty) {
            Log.e("actualizarVisibilidad", "No se encontró ningún documento con el id $motoId")
            return false
        }

        // Supone que el 'id' es único, así que debería haber solo un documento
        val documentSnapshot = querySnapshot.documents[0]
        val documentId = documentSnapshot.id

        // Actualiza este documento usando su ID
        db.collection("NuevaMotos").document(documentId)
            .update("visible", nuevoEstadoVisible)
            .await()

        true
    } catch (e: Exception) {
        Log.e("actualizarVisibilidad", "Error updating visibility", e)
        false
    }
}

suspend fun actualizarFavoritos(motoId: String, nuevoEstado: Boolean): Boolean {
    val auth = FirebaseAuth.getInstance()
    if (auth.currentUser == null) {
        auth.signInAnonymously().await()
    }
    val uid = auth.currentUser?.uid ?: return false
    val db = FirebaseFirestore.getInstance()

    try {
        // Referencia al documento del usuario en FavoritasUsuarios
        val userFavRef = db.collection("FavoritasUsuarios").document(uid)

        // Usar una transacción para actualizar la lista de motoIds
        db.runTransaction { transaction ->
            val userDoc = transaction.get(userFavRef)

            val motoIds = userDoc.get("motoId") as? List<String> ?: listOf()

            val updatedMotoIds = if (nuevoEstado) {
                if (motoId !in motoIds) motoIds + motoId else motoIds
            } else {
                motoIds - motoId
            }

            // Actualizar el documento con la nueva lista de motoIds
            transaction.set(userFavRef, mapOf(
                "uid" to uid,
                "motoId" to updatedMotoIds
            ), SetOptions.merge())
        }.await()

        Log.d("Firestore", "Lista de motoIds actualizada exitosamente para el usuario: $uid")

    } catch (e: Exception) {
        Log.e("Firestore", "Error al actualizar la lista de motoIds en Firestore", e)
        return false
    }

    // Actualizar el estado de favoritos en la colección NuevaMotos
    try {
        val querySnapshot = db.collection("NuevaMotos")
            .whereEqualTo("id", motoId)
            .get()
            .await()

        if (querySnapshot.isEmpty) {
            Log.e("actualizarFavoritos", "No se encontró ningún documento con el id $motoId")
            return false
        }

        val documentSnapshot = querySnapshot.documents[0]
        val documentId = documentSnapshot.id

        db.collection("NuevaMotos").document(documentId)
            .update("favoritos", nuevoEstado)
            .await()

        return true
    } catch (e: Exception) {
        Log.e("actualizarFavoritos", "Error updating favoritos", e)
        return false
    }
}

suspend fun eliminarDocumentoEnFirebase(motoId: String): Boolean {
    val auth = FirebaseAuth.getInstance()
    if (auth.currentUser == null) {
        auth.signInAnonymously().await()
    }

    val db = FirebaseFirestore.getInstance()
    return try {
        // Busca el documento con el campo 'id' igual a motoId
        val querySnapshot = db.collection("NuevaMotos")
            .whereEqualTo("id", motoId)
            .get()
            .await()

        if (querySnapshot.isEmpty) {
            Log.e("EliminarDocumento", "No se encontró ningún documento con el id $motoId")
            return false
        }

        // Supone que el 'id' es único, así que debería haber solo un documento
        val documentSnapshot = querySnapshot.documents[0]
        val documentId = documentSnapshot.id

        // Actualiza este documento usando su ID
        db.collection("NuevaMotos").document(documentId)
            .delete()
            .await()

        true
    } catch (e: Exception) {
        Log.e("actualizarVisibilidad", "Error updating visibility", e)
        false
    }
}

