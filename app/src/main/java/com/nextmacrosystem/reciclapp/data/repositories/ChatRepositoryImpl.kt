package com.nextmacrosystem.reciclapp.data.repositories

import com.nextmacrosystem.reciclapp.domain.repositories.ChatRepository
import android.util.Log
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.nextmacrosystem.reciclapp.domain.entities.Chat
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "ChatRepositoryImpl"
private const val COLLECTIONPATH = "chats"

class ChatRepositoryImpl @Inject constructor(private val service: FirebaseFirestore) :
    ChatRepository {

    override suspend fun saveChat(chat: Chat) {
        service.collection(COLLECTIONPATH)
            .document(chat.idChat)
            .set(chat).await()
    }

    override suspend fun getChat(idChat: String): Chat? {
        try {
            val snapshot = service.collection(COLLECTIONPATH)
                .document(idChat)
                .get()
                .await()
            return snapshot.toObject(Chat::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener mensaje por ID: ${e.message}", e)
            return null
        }
    }

    override suspend fun actualizarChat(chat: Chat) {
        service.collection(COLLECTIONPATH)
            .document(chat.idChat)
            .set(chat)
            .await()
    }

    override suspend fun eliminarChat(idChat: String) {
        service.collection(COLLECTIONPATH)
            .document(idChat)
            .delete()
            .await()
    }

    override suspend fun obtenerChatsPorUsuario(idUsuario: String): MutableList<Chat> {
        // Ejecutar ambas consultas en paralelo
        val (resultado1, resultado2) = coroutineScope {
            val query1 = async {
                service.collection(COLLECTIONPATH)
                    .whereEqualTo("idUsuario1", idUsuario)
                    .get()
                    .await()
            }
            val query2 = async {
                service.collection(COLLECTIONPATH)
                    .whereEqualTo("idUsuario2", idUsuario)
                    .get()
                    .await()
            }
            Pair(query1.await(), query2.await())
        }

        // Combinar resultados
        val chats = mutableListOf<Chat>()
        resultado1.documents.mapNotNull { it.toObject(Chat::class.java) }.forEach { chats.add(it) }
        resultado2.documents.mapNotNull { it.toObject(Chat::class.java) }.forEach { chats.add(it) }

        return chats
    }

    override suspend fun getChatByUsers(
        idUsuario1: String,
        idUsuario2: String
    ): Chat? {
        try {
            // Usar OR lógico con dos consultas pero en una sola llamada a Firestore
            val query = service.collection(COLLECTIONPATH)
                .where(
                    Filter.or(
                        Filter.and(
                            Filter.equalTo("idUsuario1", idUsuario1),
                            Filter.equalTo("idUsuario2", idUsuario2)
                        ),
                        Filter.and(
                            Filter.equalTo("idUsuario1", idUsuario2),
                            Filter.equalTo("idUsuario2", idUsuario1)
                        )
                    )
                )
                .get()
                .await()

            return query.documents.firstNotNullOfOrNull { it.toObject(Chat::class.java) }
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener chat por usuarios: ${e.message}", e)
            return null
        }
    }}