package com.example.reciclapp.presentation.ui.menu.ui


import android.graphics.Bitmap
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.reciclapp.domain.entities.UbicacionGPS
import com.example.reciclapp.presentation.viewmodel.MarkerData
import com.example.reciclapp.presentation.viewmodel.UbicacionViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

@OptIn(ExperimentalPermissionsApi::class, ExperimentalAnimationApi::class)
@Composable
fun MapsView(
    idUsuario: Int,
    ubicacionViewModel: UbicacionViewModel = hiltViewModel(),
) {
    val myCurrentLocation by ubicacionViewModel.myCurrentLocation.collectAsState()
    val mapView = rememberMapViewWithLifecycle()
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    var googleMap by remember { mutableStateOf<GoogleMap?>(null) }
    val markers = ubicacionViewModel.markers.collectAsState().value
    var showDialog by remember { mutableStateOf(false) }
    var selectedMarker by remember { mutableStateOf<MarkerData?>(null) }

    Log.d("BitmapDescriptor","BitmapDescriptor: $markers")

    // Request location permissions and fetch locations if granted
    LaunchedEffect(locationPermissionState.status) {
        if (locationPermissionState.status.isGranted) {
            ubicacionViewModel.fetchLocationsAndUsers()
            ubicacionViewModel.getMyCurrentLocation(idUsuario)
        } else {
            locationPermissionState.launchPermissionRequest()
        }
    }

    // Move camera to my current location when it changes and is not null
    LaunchedEffect(myCurrentLocation) {
        googleMap?.let { map ->
            myCurrentLocation?.let { location ->
                val myLocationLatLng = LatLng(location.latitude, location.longitude)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocationLatLng, 13f))
            }
        }
    }

    // Update map markers when markers change
    LaunchedEffect(markers) {
        googleMap?.let { map ->
            updateMapMarkers(map, markers) { markerData ->
                selectedMarker = markerData
                showDialog = true
            }
        }
    }

    AndroidView(
        factory = {
            mapView.apply {
                getMapAsync { map ->
                    googleMap = map
                    if (locationPermissionState.status.isGranted) {
                        map.isMyLocationEnabled = true
                    }
                    myCurrentLocation?.let { location ->
                        setupMap(location, map, locationPermissionState)
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    )

    if (showDialog && selectedMarker != null) {
        MarkerDialog(
            markerData = selectedMarker!!,
            onDismiss = { showDialog = false }
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun setupMap(
    myLocation: UbicacionGPS?,
    googleMap: GoogleMap,
    locationPermissionState: PermissionState
) {
    myLocation?.let {
        val location = LatLng(it.latitude, it.longitude)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f))

        if (locationPermissionState.status.isGranted) {
            googleMap.isMyLocationEnabled = true
        }
    }
}

private fun updateMapMarkers(
    googleMap: GoogleMap,
    markers: List<MarkerData>,
    onMarkerClick: (MarkerData) -> Unit
) {
    googleMap.clear()
    markers.forEach { markerData ->
        val markerOptions = MarkerOptions()
            .position(LatLng(markerData.ubicacion.latitude, markerData.ubicacion.longitude))
            .title("${markerData.usuario.nombre} ${markerData.usuario.apellido}")
            .snippet("Dirección: ${markerData.usuario.direccion}")
        markerData.bitmap?.let {
            markerOptions.icon(createCustomMarkerIcon(it))
        }
        val marker = googleMap.addMarker(markerOptions)
        marker?.tag = markerData
    }

    googleMap.setOnMarkerClickListener { marker ->
        (marker.tag as? MarkerData)?.let {
            onMarkerClick(it)
            true
        } ?: false
    }
}

private fun createCustomMarkerIcon(bitmap: Bitmap): BitmapDescriptor {
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

@Composable
fun MarkerDialog(
    markerData: MarkerData,
    onDismiss: () -> Unit
) {
    val density = LocalContext.current.resources.displayMetrics.density
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels / density

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = true,
            enter = slideInVertically { -screenHeight.toInt() } + fadeIn(),
            exit = slideOutVertically { -screenHeight.toInt() } + fadeOut()
        ) {
            AlertDialog(
                onDismissRequest = onDismiss,
                title = { Text(text = "${markerData.usuario.nombre} ${markerData.usuario.apellido}") },
                text = { Text(text = "Dirección: ${markerData.usuario.direccion}") },
                confirmButton = {
                    Button(onClick = onDismiss) {
                        Text("Cerrar")
                    }
                }
            )
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) = mapView.onCreate(null)
            override fun onStart(owner: LifecycleOwner) = mapView.onStart()
            override fun onResume(owner: LifecycleOwner) = mapView.onResume()
            override fun onPause(owner: LifecycleOwner) = mapView.onPause()
            override fun onStop(owner: LifecycleOwner) = mapView.onStop()
            override fun onDestroy(owner: LifecycleOwner) = mapView.onDestroy()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView.onDestroy()
        }
    }
    return mapView
}
