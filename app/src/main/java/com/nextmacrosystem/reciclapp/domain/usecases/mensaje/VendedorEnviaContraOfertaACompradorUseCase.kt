package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class VendedorEnviaContraOfertaACompradorUseCase @Inject constructor(private val mensajeRepository: MensajeRepository) {
    suspend operator fun invoke(
        contrapreciosMap: Map<String, Double>,
        mensaje: Mensaje,
        tokenComprador: String,
    ) {
        mensajeRepository.vendedorEnviaContraOfertaAComprador(
            contrapreciosMap,
            mensaje,
            tokenComprador
        )
    }
}