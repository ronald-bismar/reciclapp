<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/comprador/HacerOfertaPorMaterialesEnVentaUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.comprador

import com.example.reciclapp_bolivia.domain.repositories.CompradorRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.comprador

import com.nextmacrosystem.reciclapp.domain.repositories.CompradorRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/comprador/HacerOfertaPorMaterialesEnVentaUseCase.kt
import javax.inject.Inject

class HacerOfertaPorMaterialesEnVentaUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(precioPropuesto: Double) {
        return compradorRepository.hacerOfertaPorMaterialesEnVenta(precioPropuesto)
    }
}