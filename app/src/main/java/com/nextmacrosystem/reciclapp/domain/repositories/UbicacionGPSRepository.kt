package com.nextmacrosystem.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.UbicacionGPS
import com.example.reciclapp.domain.entities.Usuario

interface UbicacionGPSRepository {
    suspend fun actualizarUbicacionGPS(ubicacionGPS: UbicacionGPS)
    suspend fun registrarUbicacionDeUsuario(ubicacion: UbicacionGPS)
    suspend fun getUbicacionDeUsuario(idUsuario: String): UbicacionGPS
    suspend fun deleteUbicacion (idUbicacion: String)
    suspend fun getAllLocations(): MutableList<UbicacionGPS>
    suspend fun getLocationsAndCompradores(): MutableList<HashMap<Usuario, UbicacionGPS>>
    suspend fun getLocationsAndVendedores(): MutableList<HashMap<Usuario, UbicacionGPS>>

}