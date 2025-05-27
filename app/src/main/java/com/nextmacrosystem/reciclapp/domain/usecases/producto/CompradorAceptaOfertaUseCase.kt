package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class CompradorAceptaOfertaUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend operator fun invoke(message: Mensaje, tokenVendedor: String) {
        return productoRepository.compradorAceptaOfertaPorProductos(message, tokenVendedor)
    }
}