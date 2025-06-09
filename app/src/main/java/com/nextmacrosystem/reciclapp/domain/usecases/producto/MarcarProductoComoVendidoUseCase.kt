package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.TransaccionPendiente
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class MarcarProductoComoVendidoUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(transaccionPendiente: TransaccionPendiente) {
        productoRepository.marcarProductosComoVendido(transaccionPendiente)
    }
}