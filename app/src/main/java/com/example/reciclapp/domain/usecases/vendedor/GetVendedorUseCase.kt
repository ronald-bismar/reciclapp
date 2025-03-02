package com.example.reciclapp.domain.usecases.vendedor

import android.util.Log
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.VendedorRepository
import javax.inject.Inject

private const val TAG = "GetVendedorUseCase"

class GetVendedorUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(idVendedor: String): Usuario? {
        Log.d(TAG, "execute: GetVendedorUseCase")
        return vendedorRepository.getVendedor(idVendedor)
    }
}