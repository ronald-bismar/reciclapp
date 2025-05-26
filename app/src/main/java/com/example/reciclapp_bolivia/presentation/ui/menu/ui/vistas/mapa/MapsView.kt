package com.example.reciclapp_bolivia.presentation.ui.menu.ui.vistas.mapa

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
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
import androidx.core.graphics.drawable.toDrawable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.presentation.animations.AnimatedTransitionDialog
import com.example.reciclapp_bolivia.presentation.viewmodel.CompradoresViewModel
import com.example.reciclapp_bolivia.presentation.viewmodel.MarkerData
import com.example.reciclapp_bolivia.presentation.viewmodel.UbicacionViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapsView(
    mainNavController: NavController,
    ubicacionViewModel: UbicacionViewModel,
) {
    val myCurrentLocation by ubicacionViewModel.myCurrentLocation.collectAsState()
    val locationPermissionState =
        rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    val markers = ubicacionViewModel.markers.collectAsState().value
    var showDialog by remember { mutableStateOf(false) }
    var selectedMarker by remember { mutableStateOf<MarkerData?>(null) }

    var mapView by remember(ubicacionViewModel) { mutableStateOf<MapView?>(null) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Configuration.getInstance().load(context, context.getSharedPreferences("osm_pref", 0))
        Configuration.getInstance().userAgentValue = context.packageName
    }

    // Move camera to my current location when it changes and is not null
    LaunchedEffect(myCurrentLocation, mapView) {
        mapView?.let { view ->
            myCurrentLocation?.let { location ->
                Log.d("CurrentLocation", "My current location: $myCurrentLocation")
                val geoPoint = GeoPoint(location.latitude, location.longitude)
                view.controller.animateTo(geoPoint)
                view.controller.setZoom(13.0)
            }
        }
    }

//    // Update map markers when markers change
    LaunchedEffect(markers, mapView) {
        mapView?.let { view ->
            Log.d("MarkersUpdate", "Updating ${markers.size} markers")
            view.overlays.removeIf { it is Marker }
            // Añadir nuevos marcadores
            markers.forEach { markerData ->
                val marker = Marker(view).apply {
                    position =
                        GeoPoint(markerData.ubicacion.latitude, markerData.ubicacion.longitude)
                    title = "${markerData.usuario.nombre} ${markerData.usuario.apellido}"
                    subDescription = "Dirección: ${markerData.usuario.direccion}"

                    markerData.bitmap?.let { bitmap ->
                        val contextView = view.context
                        icon = createCustomMarkerIcon(bitmap, contextView)
                    }

                    setOnMarkerClickListener { _, _ ->
                        selectedMarker = markerData
                        showDialog = true
                        true
                    }
                }

                view.overlays.add(marker)
                marker.id = markerData.ubicacion.idUbicacionGPS

            }
            view.invalidate() // Refrescar el mapa
        }
    }

    val ctx = LocalContext.current
    Configuration.getInstance().load(ctx, ctx.getSharedPreferences("osm_pref", 0))
    Configuration.getInstance().userAgentValue = ctx.packageName

    Box(modifier = Modifier.fillMaxSize()) {
        OsmAndroidMapView(
            onMapReady = { mapView = it },
            mapView = mapView
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

@Composable
fun OsmAndroidMapView(
    onMapReady: (MapView) -> Unit,
    mapView: MapView? = null // Añadir parámetro para el mapView existente
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = { context ->
            mapView?: MapView(context).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                setBuiltInZoomControls(true)

                //Configurar eventos del mapa
                overlays.add(MapEventsOverlay(object : MapEventsReceiver {
                    override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                        return false
                    }

                    override fun longPressHelper(p: GeoPoint?): Boolean {
                        return false
                    }
                }))
                onMapReady(this)
            }
        },
        modifier = Modifier.fillMaxSize(),
    )
    // Manejo del ciclo de vida
    DisposableEffect(lifecycleOwner) {
        val observer = object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                Configuration.getInstance()
                    .load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

private fun createCustomMarkerIcon(bitmap: Bitmap, context: Context): Drawable {
    return bitmap.toDrawable(context.resources).apply{
        setBounds(0,0,intrinsicWidth, intrinsicHeight)
    }
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
                ProfileComprador(
                    markerData.usuario,
                    onDismiss = onDismiss,
                    materiales = materiales,
                    navController = mainNavController
                )

            }
        }
    }
}

@Composable
fun ProfileComprador(
    usuario: Usuario,
    materiales: List<ProductoReciclable>,
    onDismiss: () -> Unit,
    navController: NavController
) {
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
        materiales.forEach { material ->
            Text(
                text = "${material.nombreProducto} ${material.monedaDeCompra} ${material.precio} por ${material.unidadMedida}",
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onDismiss, shape = RoundedCornerShape(4.dp)) {
                Text("Cerrar")
            }
            Button(onClick = {
                val profileRoute =
                    "compradorPerfil/${usuario.idUsuario}" //vamos a pantalla perfil del comprador
                navController.navigate(profileRoute)
            }, shape = RoundedCornerShape(4.dp)) {
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
            // override fun onCreate(owner: LifecycleOwner) = mapView.onCreate(null)
            // override fun onStart(owner: LifecycleOwner) = mapView.onStart()
            override fun onResume(owner: LifecycleOwner) = mapView.onResume()
            override fun onPause(owner: LifecycleOwner) = mapView.onPause()
            // override fun onStop(owner: LifecycleOwner) = mapView.onStop()
            // override fun onDestroy(owner: LifecycleOwner) = mapView.onDestroy()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return mapView
}
