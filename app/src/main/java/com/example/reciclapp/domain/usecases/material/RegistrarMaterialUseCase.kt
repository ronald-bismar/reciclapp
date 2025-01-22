package com.example.reciclapp.domain.usecases.material

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.repositories.MaterialRepository
import javax.inject.Inject

class RegistrarMaterialUseCase @Inject constructor(private val materialRepository: MaterialRepository) {
    suspend fun execute(material: ProductoReciclable){
        return materialRepository.registrarMaterial(material)
    }
}