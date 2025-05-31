package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class CompradorEnviaMensajeAVendedorUseCase @Inject constructor(private val mensajeRepository: MensajeRepository) {
    suspend operator fun invoke(productos: List<ProductoReciclable>, message: Mensaje, tokenVendedor: String){
        mensajeRepository.compradorEnviaOfertaAVendedor(productos, message, tokenVendedor)
    }
}