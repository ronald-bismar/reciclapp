package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class ListarProductosPorUsuarioUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(idUsuario: String): MutableList<ProductoReciclable> {
        return productoRepository.listarProductosPorUsuario(idUsuario)
    }
}