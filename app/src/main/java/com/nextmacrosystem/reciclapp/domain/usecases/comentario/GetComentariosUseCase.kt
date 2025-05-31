package com.nextmacrosystem.reciclapp.domain.usecases.comentario

import com.nextmacrosystem.reciclapp.domain.entities.Comentario
import com.nextmacrosystem.reciclapp.domain.repositories.ComentarioRepository
import javax.inject.Inject

class GetComentariosUseCase @Inject constructor(private val comentarioRepository: ComentarioRepository) {
    suspend fun execute(idUsuario: String): MutableList<Comentario> {
        return comentarioRepository.getComentarios(idUsuario)
    }
}