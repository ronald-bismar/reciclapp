<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/producto/MarcarProductoComoVendidoUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.producto

import com.example.reciclapp_bolivia.domain.entities.TransaccionPendiente
import com.example.reciclapp_bolivia.domain.repositories.ProductoRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.TransaccionPendiente
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/producto/MarcarProductoComoVendidoUseCase.kt
import javax.inject.Inject

class MarcarProductoComoVendidoUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(transaccionPendiente: TransaccionPendiente) {
        productoRepository.marcarProductosComoVendido(transaccionPendiente)
    }
}