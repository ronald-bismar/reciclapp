package com.nextmacrosystem.reciclapp.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextmacrosystem.reciclapp.domain.entities.UbicacionGPS
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS.GetLocationsAndCompradoresUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS.RegistrarUbicacionDeUsuarioUseCase
import com.nextmacrosystem.reciclapp.util.GenerateID
import com.nextmacrosystem.reciclapp.util.ImageRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UbicacionViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val registrarUbicacionDeUsuarioUseCase: RegistrarUbicacionDeUsuarioUseCase,
    private val getLocationsAndCompradoresUseCase: GetLocationsAndCompradoresUseCase,
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val _ubicacionesConUsuarios =
        MutableStateFlow<MutableList<HashMap<Usuario, UbicacionGPS>>>(mutableListOf())
    val ubicacionesConUsuarios: StateFlow<MutableList<HashMap<Usuario, UbicacionGPS>>> =
        _ubicacionesConUsuarios

    private val _myCurrentLocation = MutableStateFlow<UbicacionGPS?>(null)
    val myCurrentLocation: StateFlow<UbicacionGPS?> = _myCurrentLocation

    private val _markers = MutableStateFlow<List<MarkerData>>(emptyList())
    val markers: StateFlow<List<MarkerData>> = _markers

    @SuppressLint("MissingPermission")
    fun obtenerYGuardarMiUbicacion(myUser: Usuario) {
        viewModelScope.launch {
            try {
                val location = fusedLocationClient.lastLocation.await()
                if (location != null) {
                    val ubicacionGPS = UbicacionGPS(
                        idUbicacionGPS = GenerateID(), // Genera un ID único para la ubicación
                        latitude = location.latitude,
                        longitude = location.longitude,
                        precision = location.accuracy,
                        fechaRegistro = Timestamp(java.util.Date()).toString(),
                        idUsuario = myUser.idUsuario
                    )
                    _myCurrentLocation.value = ubicacionGPS
                    registrarUbicacionDeUsuarioUseCase.execute(ubicacionGPS)
                }
            } catch (e: Exception) {
                Log.d("UbicacionViewModel", "Error al obtener la ubicación ${e.message}")
            }
        }
    }

    fun fetchLocationsAndUsers() {
        viewModelScope.launch {
            val locationsAndUsers = getLocationsAndCompradoresUseCase.execute()
            _ubicacionesConUsuarios.value = locationsAndUsers
            loadMarkerImages(locationsAndUsers)
        }
    }

    fun loadMarkerImages(locationsAndUsers: List<HashMap<Usuario, UbicacionGPS>>) {
        viewModelScope.launch {
            val newMarkers = mutableListOf<MarkerData>()

            for (entry in locationsAndUsers) {
                for ((usuario, ubicacion) in entry) {
                    val urlImagenPerfil = usuario.urlImagenPerfil
                    val bitmap = withContext(Dispatchers.IO) {
                        imageRepository.loadImage(urlImagenPerfil)
                    }
                    newMarkers.add(MarkerData(usuario, ubicacion, bitmap))
                }
            }
            _markers.value = newMarkers
        }
    }
}

data class MarkerData(
    val usuario: Usuario,
    val ubicacion: UbicacionGPS,
    val bitmap: Bitmap?,
)
