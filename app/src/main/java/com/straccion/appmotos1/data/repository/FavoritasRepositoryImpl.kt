package com.straccion.appmotos1.data.repository

import com.google.firebase.firestore.CollectionReference
import com.straccion.appmotos1.core.Constants.MOTOSFAV
import com.straccion.appmotos1.domain.model.FavoritasUsuarios
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.repository.FavoritasRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class FavoritasRepositoryImpl @Inject constructor(
    @Named(MOTOSFAV) private val motosFav: CollectionReference
) : FavoritasRepository {

    override suspend fun getMotosFavoritas(idUser: String): Flow<Response<List<FavoritasUsuarios>>> = callbackFlow {
        val snapshotListener = motosFav
            .whereEqualTo("uid", idUser)
            .addSnapshotListener { snapshot, e ->
                val motosResponse = if (snapshot != null) {
                    try {
                        val motos = snapshot.documents.mapNotNull { doc ->
                            doc.toObject(FavoritasUsuarios::class.java)?.apply {
                                motoId = doc.get("motoId") as? List<String> ?: listOf()
                                uid = doc.getString("uid") ?: doc.id
                            }
                        }
                        Response.Success(motos)
                    } catch (e: Exception) {
                        Response.Failure(e)
                    }
                } else {
                    Response.Failure(e ?: Exception("Unknown error occurred"))
                }
                trySend(motosResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun eliminarMotosFav(ids: List<String>, userId: String): Response<Boolean> {
        return try {
            val map: MutableMap<String, Any> = HashMap()
            map["motoId"] = ids

            // Busca el documento donde el campo "uid" es igual al userId
            val document = motosFav.whereEqualTo("uid", userId).get().await()

            if (!document.isEmpty) {
                // Obtiene el ID del documento y actualiza
                for (doc in document.documents) {
                    motosFav.document(doc.id).update(map).await()
                }
                Response.Success(true)
            } else {
                Response.Failure(Exception("Documento no encontrado"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }
}