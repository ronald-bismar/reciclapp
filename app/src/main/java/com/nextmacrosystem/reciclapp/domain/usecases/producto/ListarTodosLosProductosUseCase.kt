<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/producto/ListarTodosLosProductosUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.producto

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.repositories.ProductoRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/producto/ListarTodosLosProductosUseCase.kt
import javax.inject.Inject

class ListarTodosLosProductosUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(): List<ProductoReciclable>{
        return productoRepository.listarTodosLosProductos()
    }
}