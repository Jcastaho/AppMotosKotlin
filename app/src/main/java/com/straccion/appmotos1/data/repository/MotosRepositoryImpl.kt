package com.straccion.appmotos1.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.straccion.appmotos1.core.Constants.MOTOS
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.repository.MotosRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class MotosRepositoryImpl @Inject constructor(
    @Named(MOTOS) private val motosRef: CollectionReference
) : MotosRepository {

    override suspend fun getMotosVisibles(): Flow<Response<List<CategoriaMotos>>> = callbackFlow {
        val snapshotListener = motosRef
            .whereEqualTo("visible", true)
            .addSnapshotListener { snapshot, e ->
                val motosResponse = if (snapshot != null) {
                    try {
                        val motos = snapshot.documents.mapNotNull { doc ->
                            doc.toObject(CategoriaMotos::class.java)?.apply {
                                id = doc.getString("id") ?: doc.id
                                marcaMoto = ubicacionImagenes["carpeta1"] ?: ""
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

    override suspend fun getMotosById(idMoto: String): Flow<Response<List<CategoriaMotos>>> =
        callbackFlow {
            val snapshotListener = motosRef
                .whereEqualTo("visible", true)
                .whereEqualTo("id", idMoto)
                .addSnapshotListener { snapshot, e ->
                    val motosResponse = if (snapshot != null) {
                        try {
                            val motos = snapshot.documents.mapNotNull { doc ->
                                doc.toObject(CategoriaMotos::class.java)?.apply {
                                    id = doc.getString("id") ?: doc.id
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

    override suspend fun sumarVisitas(idMoto: String, busqueda: Boolean): Response<Boolean> {
        return try {
            // Busca el documento donde el campo "motoId" es igual al id proporcionado
            val document = motosRef.whereEqualTo("id", idMoto).get().await()
            val docRef = motosRef.document(document.documents.first().id)
            // Incrementa el campo "vistas" en 1
            docRef.update("vistas", FieldValue.increment(1)).await()
            if (busqueda) {
                docRef.update("busquedas", FieldValue.increment(1)).await()
            }
            Response.Success(true)

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }
}
