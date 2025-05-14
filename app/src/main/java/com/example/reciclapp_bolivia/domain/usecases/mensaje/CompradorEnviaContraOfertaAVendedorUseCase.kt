package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
import javax.inject.Inject

class CompradorEnviaContraOfertaAVendedorUseCase @Inject constructor(private val mensajeRepository: MensajeRepository) {
    suspend operator fun invoke(
        contrapreciosMap: Map<String, Double>,
        mensaje: Mensaje,
        tokenVendedor: String,
    ) {
        mensajeRepository.compradorEnviaContraOfertaAVendedor(
            contrapreciosMap,
            mensaje, tokenVendedor,
        )
    }
}