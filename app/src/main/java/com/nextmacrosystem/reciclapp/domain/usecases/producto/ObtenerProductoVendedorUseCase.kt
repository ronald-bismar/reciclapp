package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class ObtenerProductoVendedorUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(): List<Pair<ProductoReciclable, Usuario>> {
        return productoRepository.obtenerProductoYVendedor()
    }
}