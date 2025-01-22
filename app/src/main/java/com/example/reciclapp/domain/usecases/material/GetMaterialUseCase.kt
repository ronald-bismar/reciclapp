package com.example.reciclapp.domain.usecases.material

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.repositories.MaterialRepository
import javax.inject.Inject

class GetMaterialUseCase @Inject constructor(private val materialRepository: MaterialRepository) {
    suspend fun execute(idMaterial: Int): ProductoReciclable? {
        return materialRepository.getMaterial(idMaterial)
    }
}