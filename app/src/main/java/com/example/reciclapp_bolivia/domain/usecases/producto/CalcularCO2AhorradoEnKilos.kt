package com.example.reciclapp_bolivia.domain.usecases.producto

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.repositories.ProductoRepository
import javax.inject.Inject

class CalcularCO2AhorradoEnKilos @Inject constructor(private val productoRepository: ProductoRepository) {
    fun execute(productosReciclables: List<ProductoReciclable>): Double {
        return productoRepository.calcularCO2AhorradoEnKilos(productosReciclables)
    }
}