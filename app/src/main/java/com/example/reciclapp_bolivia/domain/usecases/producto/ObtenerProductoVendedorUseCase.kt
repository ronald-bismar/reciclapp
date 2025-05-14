package com.example.reciclapp_bolivia.domain.usecases.producto

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.ProductoRepository
import javax.inject.Inject

class ObtenerProductoVendedorUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(): List<Pair<ProductoReciclable, Usuario>> {
        return productoRepository.obtenerProductoYVendedor()
    }
}