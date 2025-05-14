package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.Comentario

interface ComentarioRepository {
    suspend fun crearComentario(comentario: Comentario)
    suspend fun getComentario(idComentario: String): Comentario
    suspend fun actualizarComentario(comentario: Comentario)
    suspend fun eliminarComentario(idComentario: String)
    suspend fun getComentarios(idUsuario: String): MutableList<Comentario>
    suspend fun listarComentariosDeComprador(idComprador: String): MutableList<Comentario>
}