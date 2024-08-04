package com.example.reciclapp.domain.usecases.comprador

import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.CompradorRepository
import javax.inject.Inject

class GetCompradoresUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(): MutableList<Usuario> {
        return compradorRepository.getCompradores()
    }
}