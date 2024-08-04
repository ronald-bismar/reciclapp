package com.example.reciclapp.domain.usecases.comentario

import com.example.reciclapp.domain.entities.Comentario
import com.example.reciclapp.domain.repositories.ComentarioRepository
import javax.inject.Inject

class ListarComentariosDeCompradorUseCase@Inject constructor(private val comentarioRepository: ComentarioRepository) {
    suspend fun execute(idComprador: Int): MutableList<Comentario>{
        return comentarioRepository.listarComentariosDeComprador(idComprador)
    }
}