package com.example.reciclapp.domain.usecases.mensaje

import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class CompradorEnviaContraOfertaAVendedorUseCase @Inject constructor(private val mensajeRepository: MensajeRepository) {
    suspend operator fun invoke(
        contrapreciosMap: Map<String, Double>,
        idComprador: String,
        vendedor: Usuario,
    ) {
        mensajeRepository.compradorEnviaContraOfertaAVendedor(
            contrapreciosMap,
            idComprador,
            vendedor,
        )
    }
}