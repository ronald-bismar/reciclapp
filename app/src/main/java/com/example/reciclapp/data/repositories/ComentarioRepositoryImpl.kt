package com.example.reciclapp.data.repositories

import android.util.Log
import com.example.reciclapp.domain.entities.Comentario
import com.example.reciclapp.domain.repositories.ComentarioRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ComentarioRepositoryImpl @Inject constructor(private val service: FirebaseFirestore) : ComentarioRepository {
    override suspend fun crearComentario(comentario: Comentario) {
        service.collection("comentario")
            .document(comentario.idComentario.toString())
            .set(comentario)
            .await()
    }

    override suspend fun getComentario(idComentario: Int): Comentario {
        TODO("Not yet implemented")
    }

    override suspend fun actualizarComentario(comentario: Comentario) {
        TODO("Not yet implemented")
    }

    override suspend fun eliminarComentario(idComentario: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getComentarios(idUsuario: Int): MutableList<Comentario> {
        TODO("Not yet implemented")
    }

    override suspend fun listarComentariosDeComprador(idComprador: Int): MutableList<Comentario> {
        val comentariosDeComprador = mutableListOf<Comentario>()
        val querySnapshot =
            service.collection("comentario").whereEqualTo("idUsuarioComentado", idComprador).get().await()
        for(document in querySnapshot.documents){
            val comentario = document.toObject(Comentario::class.java)
            comentario?.let { comentariosDeComprador.add(it) }
        }
        return comentariosDeComprador
    }
}