package com.straccion.appmotos1.data.repository

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.gson.JsonObject
import com.straccion.appmotos1.core.Constants.MOTOS
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.MotoDiferencias
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.repository.DataBaseRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Named

class DataBasesRepositoryImpl @Inject constructor(
    @Named(MOTOS) private val motosRef: CollectionReference,
    private val httpClient: HttpClient,
    private val json: Json
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

    fun cliente(): HttpClient {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging){
                level = LogLevel.ALL
            }
            expectSuccess = true
            install(HttpTimeout) {
                requestTimeoutMillis = 15000
            }
        }
        return client
    }

    override suspend fun actualizarMotos(): Response<String> {
        val client= cliente()
        return try {
            val response = client.post("http://10.0.2.2:8080/actualizar_motos") {
                contentType(ContentType.Application.Json)
            }

            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.bodyAsText()
                Response.Success(responseBody)
            } else {
                Response.Failure(Exception("Error: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        } finally {
            client.close()
        }
    }

    override suspend fun ActualizacionesRealizadas(): List<MotoDiferencias> {
        val client= cliente()
        return try {
            client.get("http://10.0.2.2:8080/json_diferencias") {
                contentType(ContentType.Application.Json)
            }.body()
        } catch (e: Exception) {
            // Regresa un error personalizado o maneja el caso
            println("Error al realizar la petición: ${e.localizedMessage}")
            throw e
        }
    }
}