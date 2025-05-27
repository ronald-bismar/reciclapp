package com.nextmacrosystem.reciclapp.domain.usecases.vendedor

import com.example.reciclapp.domain.entities.Comentario
import com.example.reciclapp.domain.repositories.VendedorRepository
import javax.inject.Inject

class ComentarACompradorUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(comentario: Comentario) {
        vendedorRepository.comentarAComprador(comentario)
    }
}