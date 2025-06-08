<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/producto/CalcularCO2AhorradoEnKilos.kt
package com.example.reciclapp_bolivia.domain.usecases.producto

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.repositories.ProductoRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/producto/CalcularCO2AhorradoEnKilos.kt
import javax.inject.Inject

class CalcularCO2AhorradoEnKilos @Inject constructor(private val productoRepository: ProductoRepository) {
    fun execute(productosReciclables: List<ProductoReciclable>): Double {
        return productoRepository.calcularCO2AhorradoEnKilos(productosReciclables)
    }
}