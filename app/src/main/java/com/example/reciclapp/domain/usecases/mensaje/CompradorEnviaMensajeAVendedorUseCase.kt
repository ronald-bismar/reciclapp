package com.example.reciclapp.domain.usecases.mensaje

import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class CompradorEnviaMensajeAVendedorUseCase @Inject constructor(private val mensajeRepository: MensajeRepository) {
    suspend operator fun invoke(productos: List<ProductoReciclable>, message: Mensaje, tokenVendedor: String){
        mensajeRepository.compradorEnviaOfertaAVendedor(productos, message, tokenVendedor)
    }
}