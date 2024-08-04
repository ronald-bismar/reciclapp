package com.example.reciclapp.domain.usecases.comentario

import com.example.reciclapp.domain.entities.Comentario
import com.example.reciclapp.domain.repositories.ComentarioRepository
import javax.inject.Inject

class GetComentariosUseCase @Inject constructor(private val comentarioRepository: ComentarioRepository) {
    suspend fun execute(idUsuario: Int): MutableList<Comentario> {
        return comentarioRepository.getComentarios(idUsuario)
    }
}