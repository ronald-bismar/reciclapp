package com.example.reciclapp.domain.usecases.mensaje

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class CompradorEnviaMensajeAVendedorUseCase @Inject constructor(private val mensajeRepository: MensajeRepository) {
    suspend operator fun invoke(productos: List<ProductoReciclable>, comprador: Usuario, vendedor: Usuario){
        mensajeRepository.compradorEnviaOfertaAVendedor(productos, comprador, vendedor)
    }
}