package com.nextmacrosystem.reciclapp.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.nextmacrosystem.reciclapp.domain.entities.Comentario
import com.nextmacrosystem.reciclapp.domain.repositories.ComentarioRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ComentarioRepositoryImpl @Inject constructor(private val service: FirebaseFirestore) : ComentarioRepository {
    override suspend fun crearComentario(comentario: Comentario) {
        service.collection("comentario")
            .document(comentario.idComentario.toString())
            .set(comentario)
            .await()
    }

    override suspend fun getComentario(idComentario: String): Comentario {
        TODO("Not yet implemented")
    }

    override suspend fun actualizarComentario(comentario: Comentario) {
        TODO("Not yet implemented")
    }

    override suspend fun eliminarComentario(idComentario: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getComentarios(idUsuario: String): MutableList<Comentario> {
        TODO("Not yet implemented")
    }

    override suspend fun listarComentariosDeComprador(idComprador: String): MutableList<Comentario> {
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