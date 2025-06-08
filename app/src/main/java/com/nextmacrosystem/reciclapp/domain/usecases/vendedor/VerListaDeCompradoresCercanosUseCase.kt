<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/vendedor/VerListaDeCompradoresCercanosUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.vendedor

import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.VendedorRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.vendedor

import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.VendedorRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/vendedor/VerListaDeCompradoresCercanosUseCase.kt
import javax.inject.Inject

class VerListaDeCompradoresCercanosUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    fun execute(compradores: List<Usuario>) {
        vendedorRepository.verListaDeCompradoresCercanos(compradores)
    }
}