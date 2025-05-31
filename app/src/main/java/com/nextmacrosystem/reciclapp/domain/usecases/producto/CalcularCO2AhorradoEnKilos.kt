package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class CalcularCO2AhorradoEnKilos @Inject constructor(private val productoRepository: ProductoRepository) {
    fun execute(productosReciclables: List<ProductoReciclable>): Double {
        return productoRepository.calcularCO2AhorradoEnKilos(productosReciclables)
    }
}