package com.example.reciclapp.domain.usecases.comentario

import com.example.reciclapp.domain.entities.Comentario
import com.example.reciclapp.domain.repositories.ComentarioRepository
import javax.inject.Inject

class ActualizarComentarioUseCase @Inject  constructor(private val comentarioRepository: ComentarioRepository){
    suspend fun execute(comentario: Comentario){
        return comentarioRepository.actualizarComentario(comentario)
    }
}