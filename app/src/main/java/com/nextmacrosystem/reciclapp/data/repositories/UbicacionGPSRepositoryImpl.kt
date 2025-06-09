package com.nextmacrosystem.reciclapp.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.nextmacrosystem.reciclapp.domain.entities.UbicacionGPS
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.UbicacionGPSRepository
import com.nextmacrosystem.reciclapp.domain.usecases.comprador.GetCompradoresUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.vendedor.GetVendedoresUseCase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UbicacionGPSRepositoryImpl @Inject constructor(
    private val service: FirebaseFirestore,
    private val getCompradoresUseCase: GetCompradoresUseCase,
    private val getVendedoresUseCase: GetVendedoresUseCase
) :
    UbicacionGPSRepository {

    override suspend fun actualizarUbicacionGPS(ubicacionGPS: UbicacionGPS) {
        service.collection("ubicacionGPS")
            .document(ubicacionGPS.idUsuario.toString())
            .set(ubicacionGPS)
            .await()
    }

    override suspend fun registrarUbicacionDeUsuario(ubicacion: UbicacionGPS) {
        service.collection("ubicacionGPS")
            .document(ubicacion.idUsuario.toString())
            .set(ubicacion)
            .await()
    }

    override suspend fun getUbicacionDeUsuario(idUsuario: String): UbicacionGPS {
        val querySnapshot =
            service.collection("ubicacionGPS").whereEqualTo("idUsuario", idUsuario).get().await()
        val ubicacionGPS = querySnapshot.documents.firstOrNull()?.toObject(UbicacionGPS::class.java)
        return ubicacionGPS ?: UbicacionGPS()
    }

    override suspend fun deleteUbicacion(idUbicacion: String) {
        service.collection("ubicacionGPS")
            .document(idUbicacion.toString())
            .delete()
            .await()
    }

    override suspend fun getAllLocations(): MutableList<UbicacionGPS> {
        val ubicaciones = mutableListOf<UbicacionGPS>()
        val querySnapshot = service.collection("ubicacionGPS")
            .get()
            .await()
        for (document in querySnapshot.documents) {
            val ubicacion = document.toObject(UbicacionGPS::class.java)
            ubicacion?.let { ubicaciones.add(it) }
        }
        return ubicaciones
    }

    override suspend fun getLocationsAndCompradores(): MutableList<HashMap<Usuario, UbicacionGPS>> {
        val compradores = getCompradoresUseCase.execute()
        val ubicacionesGPS = getAllLocations().associateBy { it.idUsuario }
        val compradoresYUbicaciones =
            compradores.mapNotNull { comprador ->
                val ubicacion = ubicacionesGPS[comprador.idUsuario]
                ubicacion?.let {
                    hashMapOf(comprador to ubicacion)
                }
            }.toMutableList()

        return compradoresYUbicaciones
    }

    override suspend fun getLocationsAndVendedores(): MutableList<HashMap<Usuario, UbicacionGPS>> {
        val vendedores = getVendedoresUseCase.execute()
        val ubicacionesGPS = getAllLocations().associateBy { it.idUsuario }
        val vendedoresYUbicaciones =
            vendedores.mapNotNull { vendedor ->
                val ubicacion = ubicacionesGPS[vendedor.idUsuario]
                ubicacion?.let {
                    hashMapOf(vendedor to ubicacion)
                }
            }.toMutableList()

        return vendedoresYUbicaciones
    }
}