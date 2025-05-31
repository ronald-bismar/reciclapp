package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class VendedorEnviaMensajeACompradorUseCase @Inject constructor(private val mensajeRepository: MensajeRepository) {
    suspend operator fun invoke(productos: List<ProductoReciclable>, message: Mensaje, tokenComprador: String) {
        mensajeRepository.vendedorEnviaOfertaAComprador(productos, message, tokenComprador)
    }
}