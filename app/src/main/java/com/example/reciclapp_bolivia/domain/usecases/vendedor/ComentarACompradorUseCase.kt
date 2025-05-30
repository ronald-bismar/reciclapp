package com.example.reciclapp_bolivia.domain.usecases.vendedor

import com.example.reciclapp_bolivia.domain.entities.Comentario
import com.example.reciclapp_bolivia.domain.repositories.VendedorRepository
import javax.inject.Inject

class ComentarACompradorUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(comentario: Comentario) {
        vendedorRepository.comentarAComprador(comentario)
    }
}