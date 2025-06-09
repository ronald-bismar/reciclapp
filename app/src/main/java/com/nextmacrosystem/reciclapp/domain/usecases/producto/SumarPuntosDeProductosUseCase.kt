package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class SumarPuntosDeProductosUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    fun execute(productos: List<ProductoReciclable>): Int  {
       return productoRepository.sumarPuntosDeProductos(productos)
    }
}