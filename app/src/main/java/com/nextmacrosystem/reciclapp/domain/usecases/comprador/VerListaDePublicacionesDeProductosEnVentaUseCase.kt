<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/comprador/VerListaDePublicacionesDeProductosEnVentaUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.comprador

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.CompradorRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.comprador

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.CompradorRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/comprador/VerListaDePublicacionesDeProductosEnVentaUseCase.kt
import javax.inject.Inject

class VerListaDePublicacionesDeProductosEnVentaUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(vendedores: List<Usuario>): List<HashMap<Usuario, ProductoReciclable>> {
        return compradorRepository.verListaDePublicacionesDeProductosEnVenta(vendedores)
    }
}