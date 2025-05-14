package com.example.reciclapp_bolivia.domain.usecases.vendedor

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.VendedorRepository
import javax.inject.Inject

class PublicarProductoUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(productoReciclable: ProductoReciclable, vendedor: Usuario) {
        vendedorRepository.publicarProducto(productoReciclable, vendedor)
    }
}