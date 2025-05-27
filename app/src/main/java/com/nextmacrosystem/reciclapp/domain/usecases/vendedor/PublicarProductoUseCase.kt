package com.nextmacrosystem.reciclapp.domain.usecases.vendedor

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.VendedorRepository
import javax.inject.Inject

class PublicarProductoUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(productoReciclable: ProductoReciclable, vendedor: Usuario) {
        vendedorRepository.publicarProducto(productoReciclable, vendedor)
    }
}