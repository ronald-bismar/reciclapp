package com.example.reciclapp_bolivia.domain.usecases.comentario

import com.example.reciclapp_bolivia.domain.entities.Comentario
import com.example.reciclapp_bolivia.domain.repositories.ComentarioRepository
import javax.inject.Inject

class GetComentarioUseCase @Inject constructor(private val comentarioRepository: ComentarioRepository){
    suspend fun execute(idComentario: String): Comentario {
        return comentarioRepository.getComentario(idComentario)
    }

}