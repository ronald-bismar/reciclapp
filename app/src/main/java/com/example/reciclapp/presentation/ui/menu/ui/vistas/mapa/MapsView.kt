package com.example.reciclapp.presentation.ui.menu.ui.vistas.mapa


import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.UbicacionGPS
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.presentation.animations.AnimatedTransitionDialog
import com.example.reciclapp.presentation.viewmodel.CompradoresViewModel
import com.example.reciclapp.presentation.viewmodel.MarkerData
import com.example.reciclapp.presentation.viewmodel.UbicacionViewModel
import com.example.reciclapp.presentation.viewmodel.UserViewModel
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
import com.google.android.gms.maps.model.MarkerOptions

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapsView(
    mainNavController: NavController,
    ubicacionViewModel: UbicacionViewModel,
) {
    val myCurrentLocation by ubicacionViewModel.myCurrentLocation.collectAsState()
    val mapView = rememberMapViewWithLifecycle()
    val locationPermissionState =
        rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    var googleMap by remember { mutableStateOf<GoogleMap?>(null) }
    val markers = ubicacionViewModel.markers.collectAsState().value
    var showDialog by remember { mutableStateOf(false) }
    var selectedMarker by remember { mutableStateOf<MarkerData?>(null) }

    // Move camera to my current location when it changes and is not null
    LaunchedEffect(myCurrentLocation) {
        Log.d("CurrentLocation", "My current location: $myCurrentLocation")
        googleMap?.let { map ->
            myCurrentLocation?.let { location ->
                val myLocationLatLng = LatLng(location.latitude, location.longitude)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocationLatLng, 13f))
            }
        }
    }

    // Update map markers when markers change
    LaunchedEffect(markers, googleMap) {
        Log.d("Markers", "Markers: $markers")
        googleMap?.let { map ->
            updateMapMarkers(map, markers) { markerData ->
                selectedMarker = markerData
                showDialog = true
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Map view
        AndroidView(
            factory = {
                mapView.apply {
                    getMapAsync @androidx.annotation.RequiresPermission(allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION]) { map ->
                        googleMap = map
                        if (locationPermissionState.status.isGranted) {
                            map.isMyLocationEnabled = true
                        }
                        myCurrentLocation?.let { location ->
                            setupMap(location, map, locationPermissionState)
                        }
                        map.setOnMarkerClickListener { marker ->
                            googleMap?.let {
                                val point = map.projection.toScreenLocation(marker.position)
                                Log.d(
                                    "Posicion de clic en mapa",
                                    "Pantalla x: ${point.x}, y: ${point.y}"
                                )
                                (marker.tag as? MarkerData)?.let {
                                    selectedMarker = it
                                    showDialog = true
                                    true
                                } ?: false
                            } ?: false
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Title overlay
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(15.dp)
                .background(Color.White.copy(alpha = 0.4f), shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Mapa Compradores de Reciclaje",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
        }

        // Display the dialog
        if (showDialog && selectedMarker != null) {
            MarkerDialog(
                markerData = selectedMarker!!,
                onDismiss = { showDialog = false },
                mainNavController = mainNavController
            )
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
private fun setupMap(
    myLocation: UbicacionGPS?,
    googleMap: GoogleMap,
    locationPermissionState: PermissionState
) {
    myLocation?.let { location ->
        val latLng = LatLng(location.latitude, location.longitude)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f))

        if (locationPermissionState.status.isGranted) {
            @SuppressLint("MissingPermission")
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MarkerDialog(
    markerData: MarkerData,
    onDismiss: () -> Unit,
    mainNavController: NavController,
    compradoresViewModel: CompradoresViewModel = hiltViewModel()
) {

    val materiales = compradoresViewModel.productos.collectAsState().value

    markerData.usuario.idUsuario?.let { compradoresViewModel.fetchMaterialesByComprador(it) }

    AnimatedTransitionDialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                profileComprador(markerData.usuario, onDismiss = onDismiss, materiales = materiales, navController = mainNavController)

            }
        }
    }
}

@Composable
fun profileComprador(usuario: Usuario, materiales: List<ProductoReciclable>, onDismiss: () -> Unit, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = usuario.tipoDeUsuario.capitalize(),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Image(
            painter = rememberAsyncImagePainter(model = usuario.urlImagenPerfil),
            contentDescription = null,
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(text = "${usuario.nombre} ${usuario.apellido}", color = Color.Black)
        Text(text = usuario.telefono.toString())
        Text(text = usuario.correo)
        Text(text = usuario.puntaje.toString())
        HorizontalDivider()
        Text(
            text = "Compro: ",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        materiales.forEach{ material ->
            Text(text = "${material.nombreProducto} ${material.monedaDeCompra} ${material.precio} por ${material.unidadMedida}", color = Color.Black)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row (modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly){
            Button(onClick = onDismiss, shape = RoundedCornerShape(4.dp)) {
                Text("Cerrar")
            }
            Button(onClick = { val profileRoute =
                    "compradorPerfil/${usuario.idUsuario}" //vamos a pantalla perfil del comprador
                navController.navigate(profileRoute) }, shape = RoundedCornerShape(4.dp)) {
                Text("Contactar")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

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
        }
    }
    return mapView
}
