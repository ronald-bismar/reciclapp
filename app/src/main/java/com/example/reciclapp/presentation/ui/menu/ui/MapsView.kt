import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.reciclapp.domain.entities.UbicacionGPS
import com.example.reciclapp.domain.entities.Usuario
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
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapsView(
    idUsuario: Int,
    ubicacionViewModel: UbicacionViewModel = hiltViewModel(),
) {
    val myCurrentLocation by ubicacionViewModel.myCurrentLocation.collectAsState()
    val mapView = rememberMapViewWithLifecycle()
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    val ubicacionesConUsuarios by ubicacionViewModel.ubicacionesConUsuarios.collectAsState()
    val context = LocalContext.current
    var googleMap by remember { mutableStateOf<GoogleMap?>(null) }

    // Request location permissions and fetch locations if granted
    LaunchedEffect(locationPermissionState.status) {
        if (locationPermissionState.status.isGranted) {
            ubicacionViewModel.fetchLocationsAndUsers()
            ubicacionViewModel.getMyCurrentLocation(idUsuario)
        } else {
            locationPermissionState.launchPermissionRequest()
        }
    }

    // Load user locations and images
    LaunchedEffect(ubicacionesConUsuarios) {
        googleMap?.let { map ->
            val markers = ubicacionesConUsuarios.flatMap { entry ->
                entry.map { (usuario, ubicacion) ->
                    MarkerData(usuario, ubicacion, null) // Set image later
                }
            }
            ubicacionViewModel.loadMarkerImages(ubicacionesConUsuarios)
            updateMapMarkers(map, markers)
        }
    }

    AndroidView(
        factory = {
            mapView.apply {
                getMapAsync { map ->
                    googleMap = map
                    myCurrentLocation?.let { location ->
                        setupMap(location, map, locationPermissionState)
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalPermissionsApi::class)
private fun setupMap(
    myLocation: UbicacionGPS,
    googleMap: GoogleMap,
    locationPermissionState: PermissionState
) {
    val location = LatLng(myLocation.latitude, myLocation.longitude)
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))

    if (locationPermissionState.status.isGranted) {
        googleMap.isMyLocationEnabled = true
    }
}

private fun updateMapMarkers(
    googleMap: GoogleMap,
    markers: List<MarkerData>
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

        googleMap.addMarker(markerOptions)
    }
}

private fun createCustomMarkerIcon(bitmap: Bitmap): BitmapDescriptor {
    return BitmapDescriptorFactory.fromBitmap(bitmap)
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
