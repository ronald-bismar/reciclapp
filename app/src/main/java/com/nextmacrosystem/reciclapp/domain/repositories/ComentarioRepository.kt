<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/repositories/ComentarioRepository.kt
package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.Comentario
========
package com.nextmacrosystem.reciclapp.domain.repositories

import com.nextmacrosystem.reciclapp.domain.entities.Comentario
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/repositories/ComentarioRepository.kt

interface ComentarioRepository {
    suspend fun crearComentario(comentario: Comentario)
    suspend fun getComentario(idComentario: String): Comentario
    suspend fun actualizarComentario(comentario: Comentario)
    suspend fun eliminarComentario(idComentario: String)
    suspend fun getComentarios(idUsuario: String): MutableList<Comentario>
    suspend fun listarComentariosDeComprador(idComprador: String): MutableList<Comentario>
}