package com.example.reciclapp.domain.usecases.producto

import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class CompradorAceptaOfertaUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend operator fun invoke(idProductosConPrecioAceptados: String, comprador: Usuario, vendedor: Usuario) {
        return productoRepository.compradorAceptaOfertaPorProductos(idProductosConPrecioAceptados, comprador, vendedor)
    }
}