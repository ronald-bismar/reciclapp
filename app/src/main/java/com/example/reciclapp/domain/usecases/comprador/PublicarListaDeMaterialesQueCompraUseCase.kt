package com.example.reciclapp.domain.usecases.comprador

import com.example.reciclapp.domain.entities.Material
import com.example.reciclapp.domain.repositories.CompradorRepository
import javax.inject.Inject

class PublicarListaDeMaterialesQueCompraUseCase  @Inject constructor(private val compradorRepository: CompradorRepository){
    suspend fun execute(materiales: List<Material>) {
        return compradorRepository.publicarListaDeMaterialesQueCompra(materiales)
    }
}