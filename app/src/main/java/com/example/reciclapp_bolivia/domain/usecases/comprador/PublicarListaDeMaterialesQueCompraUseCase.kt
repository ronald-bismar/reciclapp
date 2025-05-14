package com.example.reciclapp_bolivia.domain.usecases.comprador

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.repositories.CompradorRepository
import javax.inject.Inject

class PublicarListaDeMaterialesQueCompraUseCase  @Inject constructor(private val compradorRepository: CompradorRepository){
    suspend fun execute(materiales: List<ProductoReciclable>) {
        return compradorRepository.publicarListaDeMaterialesQueCompra(materiales)
    }
}