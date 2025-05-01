package com.example.reciclapp.data.repositories

import android.util.Log
import com.example.reciclapp.domain.entities.Chat
import com.example.reciclapp.domain.repositories.ChatRepository
import com.google.firebase.firestore.FirebaseFirestore
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
    }}