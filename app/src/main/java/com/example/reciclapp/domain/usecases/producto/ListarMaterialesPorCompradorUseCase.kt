package com.example.reciclapp.domain.usecases.producto

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class ListarMaterialesPorCompradorUseCase @Inject constructor(private val materialRepository: ProductoRepository) {
    suspend fun execute(idUsuario: Int): MutableList<ProductoReciclable> {
        return materialRepository.listarMaterialesPorComprador(idUsuario)
    }
}