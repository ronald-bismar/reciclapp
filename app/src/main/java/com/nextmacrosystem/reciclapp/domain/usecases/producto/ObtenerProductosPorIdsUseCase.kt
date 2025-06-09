package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class ObtenerProductosPorIdsUseCase @Inject constructor(private val repository: ProductoRepository) {
    suspend operator fun invoke(idsProductos: List<String>): List<ProductoReciclable>{
        return repository.obtenerProductosPorIds(idsProductos)
    }
}