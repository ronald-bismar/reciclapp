package com.nextmacrosystem.reciclapp.domain.usecases.comprador

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.CompradorRepository
import javax.inject.Inject

class PublicarListaDeMaterialesQueCompraUseCase  @Inject constructor(private val compradorRepository: CompradorRepository){
    suspend fun execute(materiales: List<ProductoReciclable>) {
        return compradorRepository.publicarListaDeMaterialesQueCompra(materiales)
    }
}