package com.example.reciclapp.domain.usecases.producto

import com.example.reciclapp.domain.entities.Producto
import com.example.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class ListarProductosDeVendedorUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(idUsuario: Int): MutableList<Producto> {
        return productoRepository.listarProductosPorVendedor(idUsuario)
    }
}