package com.example.reciclapp_bolivia.domain.usecases.comentario

import com.example.reciclapp_bolivia.domain.repositories.ComentarioRepository
import javax.inject.Inject

class EliminarComentarioUseCase @Inject constructor(private val comentarioRepository: ComentarioRepository) {
    suspend fun execute(idComentario: String) {
        return comentarioRepository.eliminarComentario(idComentario)
    }
}