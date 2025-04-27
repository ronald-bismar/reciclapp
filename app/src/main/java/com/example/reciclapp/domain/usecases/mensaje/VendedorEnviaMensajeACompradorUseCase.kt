package com.example.reciclapp.domain.usecases.mensaje

import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class VendedorEnviaMensajeACompradorUseCase @Inject constructor(private val mensajeRepository: MensajeRepository) {
    suspend operator fun invoke(productos: List<ProductoReciclable>, message: Mensaje, tokenComprador: String) {
        mensajeRepository.vendedorEnviaOfertaAComprador(productos, message, tokenComprador)
    }
}