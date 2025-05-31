package com.nextmacrosystem.reciclapp.domain.usecases.comprador

import com.nextmacrosystem.reciclapp.domain.repositories.CompradorRepository
import javax.inject.Inject

class EliminarCompradorUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(idComprador: String) {
        return compradorRepository.eliminarComprador(idComprador)
    }

}