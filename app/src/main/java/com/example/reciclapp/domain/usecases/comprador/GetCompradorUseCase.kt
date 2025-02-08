package com.example.reciclapp.domain.usecases.comprador

import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.CompradorRepository
import javax.inject.Inject

class GetCompradorUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(idComprador: String): Usuario? {
        return compradorRepository.getComprador(idComprador)
    }
}