<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/vendedor/GetVendedorUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.vendedor

import android.util.Log
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.VendedorRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.vendedor

import android.util.Log
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.VendedorRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/vendedor/GetVendedorUseCase.kt
import javax.inject.Inject

private const val TAG = "GetVendedorUseCase"

class GetVendedorUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(idVendedor: String): Usuario? {
        Log.d(TAG, "execute: GetVendedorUseCase")
        return vendedorRepository.getVendedor(idVendedor)
    }
}