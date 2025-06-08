<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/repositories/UbicacionGPSRepository.kt
package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.UbicacionGPS
import com.example.reciclapp_bolivia.domain.entities.Usuario
========
package com.nextmacrosystem.reciclapp.domain.repositories

import com.nextmacrosystem.reciclapp.domain.entities.UbicacionGPS
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/repositories/UbicacionGPSRepository.kt

interface UbicacionGPSRepository {
    suspend fun actualizarUbicacionGPS(ubicacionGPS: UbicacionGPS)
    suspend fun registrarUbicacionDeUsuario(ubicacion: UbicacionGPS)
    suspend fun getUbicacionDeUsuario(idUsuario: String): UbicacionGPS
    suspend fun deleteUbicacion (idUbicacion: String)
    suspend fun getAllLocations(): MutableList<UbicacionGPS>
    suspend fun getLocationsAndCompradores(): MutableList<HashMap<Usuario, UbicacionGPS>>
    suspend fun getLocationsAndVendedores(): MutableList<HashMap<Usuario, UbicacionGPS>>

}