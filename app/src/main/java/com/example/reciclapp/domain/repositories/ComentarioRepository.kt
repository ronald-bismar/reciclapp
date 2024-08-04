package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.Comentario
import com.example.reciclapp.domain.entities.Usuario

interface ComentarioRepository {
    suspend fun crearComentario(comentario: Comentario)
    suspend fun getComentario(idComentario: Int): Comentario
    suspend fun actualizarComentario(comentario: Comentario)
    suspend fun eliminarComentario(idComentario: Int)
    suspend fun getComentarios(idUsuario: Int): MutableList<Comentario>
    suspend fun listarComentariosDeComprador(idComprador: Int): MutableList<Comentario>
}