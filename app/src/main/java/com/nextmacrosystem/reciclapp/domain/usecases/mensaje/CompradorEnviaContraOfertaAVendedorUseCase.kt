package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
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