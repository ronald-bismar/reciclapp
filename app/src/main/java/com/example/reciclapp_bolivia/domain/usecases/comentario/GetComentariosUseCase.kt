package com.example.reciclapp_bolivia.domain.usecases.comentario

import com.example.reciclapp_bolivia.domain.entities.Comentario
import com.example.reciclapp_bolivia.domain.repositories.ComentarioRepository
import javax.inject.Inject

class GetComentariosUseCase @Inject constructor(private val comentarioRepository: ComentarioRepository) {
    suspend fun execute(idUsuario: String): MutableList<Comentario> {
        return comentarioRepository.getComentarios(idUsuario)
    }
}