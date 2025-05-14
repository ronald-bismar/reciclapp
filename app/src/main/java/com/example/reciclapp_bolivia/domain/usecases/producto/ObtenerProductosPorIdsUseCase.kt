package com.example.reciclapp_bolivia.domain.usecases.producto

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.repositories.ProductoRepository
import javax.inject.Inject

class ObtenerProductosPorIdsUseCase @Inject constructor(private val repository: ProductoRepository) {
    suspend operator fun invoke(idsProductos: List<String>): List<ProductoReciclable>{
        return repository.obtenerProductosPorIds(idsProductos)
    }
}