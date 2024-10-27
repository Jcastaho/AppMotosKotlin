package com.straccion.appmotos1.data.repository

import com.google.firebase.firestore.CollectionReference
import com.straccion.appmotos1.core.Constants.MOTOS
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.repository.DataBaseRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class DataBasesRepositoryImpl @Inject constructor(
    @Named(MOTOS) private val motosRef: CollectionReference
): DataBaseRepository{
    override suspend fun update(fichaItems: Map<String, Any>, motoId: String): Response<Boolean> {
        return try {
            val map: MutableMap<String, Any> = HashMap()
            map["fichaTecnica"] = fichaItems

            val document = motosRef.whereEqualTo("id", motoId).get().await()

            if (!document.isEmpty) {
                // Obtiene el ID del documento y actualiza
                for (doc in document.documents) {
                    motosRef.document(doc.id).update(map).await()
                }
                Response.Success(true)
            } else {
                Response.Failure(Exception("Moto no encontrado"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun ocultarMotocicleta(visible: Boolean, motoId: String): Response<Boolean> {
        return try {
            val document = motosRef.whereEqualTo("id", motoId).get().await()

            if (!document.isEmpty) {
                // Obtiene el ID del documento y actualiza
                for (doc in document.documents) {
                    doc.reference.update("visible", visible).await()
                }
                Response.Success(true)
            } else {
                Response.Failure(Exception("Moto no encontrado"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun delete(motoId: String): Response<Boolean> {
        return try {
            val document = motosRef.whereEqualTo("id", motoId).get().await()

            if (!document.isEmpty) {
                // Obtiene el ID del documento y actualiza
                for (doc in document.documents) {
                    motosRef.document(doc.id).delete().await()
                }
                Response.Success(true)
            } else {
                Response.Failure(Exception("Moto no encontrado"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun getAllMotos(): Flow<Response<List<CategoriaMotos>>> = callbackFlow {
        val snapshotListener = motosRef
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
}