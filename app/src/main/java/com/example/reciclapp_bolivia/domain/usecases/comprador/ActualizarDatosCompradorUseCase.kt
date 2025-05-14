package com.example.reciclapp_bolivia.domain.usecases.comprador

import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.CompradorRepository
import javax.inject.Inject

class ActualizarDatosCompradorUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(comprador: Usuario) {
return compradorRepository.actualizarDatosComprador(comprador)
    }
}