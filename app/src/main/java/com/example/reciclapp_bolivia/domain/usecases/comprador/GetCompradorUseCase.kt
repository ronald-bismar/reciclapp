package com.example.reciclapp_bolivia.domain.usecases.comprador

import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.CompradorRepository
import javax.inject.Inject

class GetCompradorUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(idComprador: String): Usuario? {
        return compradorRepository.getComprador(idComprador)
    }
}