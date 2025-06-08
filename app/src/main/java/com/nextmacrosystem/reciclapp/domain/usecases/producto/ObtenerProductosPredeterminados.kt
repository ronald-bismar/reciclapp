<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/producto/ObtenerProductosPredeterminados.kt
package com.example.reciclapp_bolivia.domain.usecases.producto

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.repositories.ProductoRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/producto/ObtenerProductosPredeterminados.kt
import javax.inject.Inject

private const val TAG = "ObtenerProductosPredeterminados"

class ObtenerProductosPredeterminados @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(): MutableList<ProductoReciclable> {
        return productoRepository.obtenerProductosPredeterminados()
    }
}