package com.straccion.appmotos1.data.repository

import com.google.firebase.firestore.CollectionReference
import com.straccion.appmotos1.core.Constants.MOTOS
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.repository.MotosRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Named

class MotosRepositoryImpl @Inject constructor(
    @Named(MOTOS) private val motosRef: CollectionReference
): MotosRepository {

    override suspend fun getMotos(): Flow<Response<List<CategoriaMotos>>> = callbackFlow {
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

    override suspend fun getMotosById(idMoto: String): Flow<Response<List<CategoriaMotos>>> = callbackFlow {
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
}
