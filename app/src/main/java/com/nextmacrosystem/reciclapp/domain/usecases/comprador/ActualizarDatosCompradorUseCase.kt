package com.nextmacrosystem.reciclapp.domain.usecases.comprador

import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.CompradorRepository
import javax.inject.Inject

class ActualizarDatosCompradorUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(comprador: Usuario) {
return compradorRepository.actualizarDatosComprador(comprador)
    }
}