package com.example.reciclapp.domain.usecases.producto

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class CalcularCO2AhorradoEnKilos @Inject constructor(private val productoRepository: ProductoRepository) {
    fun execute(productosReciclables: List<ProductoReciclable>): Double {
        return productoRepository.calcularCO2AhorradoEnKilos(productosReciclables)
    }
}