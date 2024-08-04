package com.example.reciclapp.domain.usecases.producto

import com.example.reciclapp.domain.entities.Producto
import com.example.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class ListarTodosLosProductosUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(): List<Producto>{
        return productoRepository.listarTodosLosProductos()
    }
}