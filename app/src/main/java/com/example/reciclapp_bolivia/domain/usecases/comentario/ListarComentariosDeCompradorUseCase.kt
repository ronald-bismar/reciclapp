package com.example.reciclapp_bolivia.domain.usecases.comentario

import com.example.reciclapp_bolivia.domain.entities.Comentario
import com.example.reciclapp_bolivia.domain.repositories.ComentarioRepository
import javax.inject.Inject

class ListarComentariosDeCompradorUseCase@Inject constructor(private val comentarioRepository: ComentarioRepository) {
    suspend fun execute(idComprador: String): MutableList<Comentario>{
        return comentarioRepository.listarComentariosDeComprador(idComprador)
    }
}