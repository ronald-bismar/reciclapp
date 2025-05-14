package com.example.reciclapp_bolivia.domain.usecases.producto

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.repositories.ProductoRepository
import javax.inject.Inject

class SumarPuntosDeProductosUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    fun execute(productos: List<ProductoReciclable>): Int  {
       return productoRepository.sumarPuntosDeProductos(productos)
    }
}