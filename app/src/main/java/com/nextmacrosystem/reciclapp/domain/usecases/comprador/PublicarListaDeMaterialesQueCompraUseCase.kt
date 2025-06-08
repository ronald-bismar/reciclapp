<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/comprador/PublicarListaDeMaterialesQueCompraUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.comprador

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.repositories.CompradorRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.comprador

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.CompradorRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/comprador/PublicarListaDeMaterialesQueCompraUseCase.kt
import javax.inject.Inject

class PublicarListaDeMaterialesQueCompraUseCase  @Inject constructor(private val compradorRepository: CompradorRepository){
    suspend fun execute(materiales: List<ProductoReciclable>) {
        return compradorRepository.publicarListaDeMaterialesQueCompra(materiales)
    }
}