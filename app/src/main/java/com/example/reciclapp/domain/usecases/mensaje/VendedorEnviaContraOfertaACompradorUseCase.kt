package com.example.reciclapp.domain.usecases.mensaje

import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class VendedorEnviaContraOfertaACompradorUseCase @Inject constructor(private val mensajeRepository: MensajeRepository) {
    suspend operator fun invoke(
        contrapreciosMap: Map<String, Double>,
        idVendedor: String,
        comprador: Usuario,
    ) {
        mensajeRepository.vendedorEnviaContraOfertaAComprador(
            contrapreciosMap,
            idVendedor,
            comprador
        )
    }
}