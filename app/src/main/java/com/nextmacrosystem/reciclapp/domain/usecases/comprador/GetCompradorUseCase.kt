package com.nextmacrosystem.reciclapp.domain.usecases.comprador

import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.CompradorRepository
import javax.inject.Inject

class GetCompradorUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(idComprador: String): Usuario? {
        return compradorRepository.getComprador(idComprador)
    }
}