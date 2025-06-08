<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/producto/ObtenerProductosPorIdsUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.producto

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.repositories.ProductoRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/producto/ObtenerProductosPorIdsUseCase.kt
import javax.inject.Inject

class ObtenerProductosPorIdsUseCase @Inject constructor(private val repository: ProductoRepository) {
    suspend operator fun invoke(idsProductos: List<String>): List<ProductoReciclable>{
        return repository.obtenerProductosPorIds(idsProductos)
    }
}