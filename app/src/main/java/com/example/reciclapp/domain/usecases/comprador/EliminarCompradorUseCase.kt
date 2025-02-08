package com.example.reciclapp.domain.usecases.comprador

import com.example.reciclapp.domain.repositories.CompradorRepository
import javax.inject.Inject

class EliminarCompradorUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(idComprador: String) {
        return compradorRepository.eliminarComprador(idComprador)
    }

}