package com.nextmacrosystem.reciclapp.domain.usecases.comprador

import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.CompradorRepository
import javax.inject.Inject

class ActualizarDatosCompradorUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(comprador: Usuario) {
return compradorRepository.actualizarDatosComprador(comprador)
    }
}