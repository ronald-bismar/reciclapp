<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/producto/CompradorAceptaOfertaUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.producto

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.repositories.ProductoRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/producto/CompradorAceptaOfertaUseCase.kt
import javax.inject.Inject

class CompradorAceptaOfertaUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend operator fun invoke(message: Mensaje, tokenVendedor: String) {
        return productoRepository.compradorAceptaOfertaPorProductos(message, tokenVendedor)
    }
}