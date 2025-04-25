package com.example.reciclapp.domain.usecases.producto

import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class VendedorAceptaOfertaUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend operator fun invoke(message: Mensaje, tokenComprador: String) {
        return productoRepository.vendedorAceptaOfertaPorProductos(message, tokenComprador)
    }
}