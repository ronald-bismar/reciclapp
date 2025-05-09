package com.example.reciclapp.domain.usecases.producto

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class ObtenerProductoVendedorUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(): List<Pair<ProductoReciclable, Usuario>> {
        return productoRepository.obtenerProductoYVendedor()
    }
}