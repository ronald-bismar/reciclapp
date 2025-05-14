package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
import javax.inject.Inject

class CompradorEnviaMensajeAVendedorUseCase @Inject constructor(private val mensajeRepository: MensajeRepository) {
    suspend operator fun invoke(productos: List<ProductoReciclable>, message: Mensaje, tokenVendedor: String){
        mensajeRepository.compradorEnviaOfertaAVendedor(productos, message, tokenVendedor)
    }
}