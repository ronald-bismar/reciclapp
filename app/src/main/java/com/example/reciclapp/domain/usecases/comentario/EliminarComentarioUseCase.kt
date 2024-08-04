package com.example.reciclapp.domain.usecases.comentario

import com.example.reciclapp.domain.repositories.ComentarioRepository
import javax.inject.Inject

class EliminarComentarioUseCase @Inject constructor(private val comentarioRepository: ComentarioRepository) {
    suspend fun execute(idComentario: Int) {
        return comentarioRepository.eliminarComentario(idComentario)
    }
}