<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/vendedor/VerMapaConCompradoresCercanosUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.vendedor

import com.example.reciclapp_bolivia.domain.entities.UbicacionGPS
import com.example.reciclapp_bolivia.domain.repositories.VendedorRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.vendedor

import com.nextmacrosystem.reciclapp.domain.entities.UbicacionGPS
import com.nextmacrosystem.reciclapp.domain.repositories.VendedorRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/vendedor/VerMapaConCompradoresCercanosUseCase.kt
import javax.inject.Inject

class VerMapaConCompradoresCercanosUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(ubicacionGPS: UbicacionGPS) {
        vendedorRepository.verMapaConCompradoresCercanos(ubicacionGPS)
    }
}
