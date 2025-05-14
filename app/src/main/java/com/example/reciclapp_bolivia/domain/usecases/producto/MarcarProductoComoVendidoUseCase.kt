package com.example.reciclapp_bolivia.domain.usecases.producto

import com.example.reciclapp_bolivia.domain.entities.TransaccionPendiente
import com.example.reciclapp_bolivia.domain.repositories.ProductoRepository
import javax.inject.Inject

class MarcarProductoComoVendidoUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(transaccionPendiente: TransaccionPendiente) {
        productoRepository.marcarProductosComoVendido(transaccionPendiente)
    }
}