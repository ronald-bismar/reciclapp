package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

private const val TAG = "ObtenerProductosPredeterminados"

class ObtenerProductosPredeterminados @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(): MutableList<ProductoReciclable> {
        return productoRepository.obtenerProductosPredeterminados()
    }
}