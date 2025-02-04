package com.example.reciclapp.domain.usecases.comprador

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.repositories.CompradorRepository
import javax.inject.Inject

class PublicarListaDeMaterialesQueCompraUseCase  @Inject constructor(private val compradorRepository: CompradorRepository){
    suspend fun execute(materiales: List<ProductoReciclable>) {
        return compradorRepository.publicarListaDeMaterialesQueCompra(materiales)
    }
}