package com.example.reciclapp.domain.usecases.vendedor

import com.example.reciclapp.domain.entities.Producto
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.VendedorRepository
import javax.inject.Inject

class PublicarProductoUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(producto: Producto, vendedor: Usuario) {
        vendedorRepository.publicarProducto(producto, vendedor)
    }
}