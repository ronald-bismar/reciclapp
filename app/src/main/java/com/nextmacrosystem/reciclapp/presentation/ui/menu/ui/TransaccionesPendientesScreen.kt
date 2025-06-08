<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/ui/menu/ui/TransaccionesPendientesScreen.kt
package com.example.reciclapp_bolivia.presentation.ui.menu.ui
========
package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/ui/menu/ui/TransaccionesPendientesScreen.kt

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/ui/menu/ui/TransaccionesPendientesScreen.kt
import com.example.reciclapp_bolivia.domain.entities.EstadoTransaccion
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.TransaccionPendiente
import com.example.reciclapp_bolivia.presentation.viewmodel.TransaccionViewModel
import com.example.reciclapp_bolivia.util.NameRoutes.QRSCANNER
========
import com.nextmacrosystem.reciclapp.domain.entities.EstadoTransaccion
import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.entities.TransaccionPendiente
import com.nextmacrosystem.reciclapp.presentation.viewmodel.TransaccionViewModel
import com.nextmacrosystem.reciclapp.util.NameRoutes.QRSCANNER
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/ui/menu/ui/TransaccionesPendientesScreen.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransaccionesPendientesScreen(
    transaccionViewModel: TransaccionViewModel,
    navController: NavController
) {
    val transacciones by transaccionViewModel.transaccionesPendientes.collectAsState()
    val productos by transaccionViewModel.productos.collectAsState()

    // Convertir la lista de productos a un mapa para acceso O(1)
    val productosMap = remember(productos) {
        productos.associateBy { it.idProducto }
    }

    LaunchedEffect(Unit) {
        transaccionViewModel.cargarTransaccionesPendientes()
    }

    Scaffold { paddingValues ->
        if (transacciones.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay transacciones pendientes",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(transacciones) { transaccion ->
                    // Obtener todos los productos de esta transacción
                    val productosTransaccion = remember(transaccion.idsProductos) {
                        transaccion.idsProductos.split(",")
                            .map { it.trim() }
                            .mapNotNull { productosMap[it] }
                    }

                    TransaccionPendienteItem(
                        transaccion = transaccion,
                        productos = productosTransaccion, // Ahora pasamos la lista completa
                        onScanQR = {
                            navController.navigate(QRSCANNER)
                        }
                    )
                }
            }        }
    }
}

@Composable
fun TransaccionPendienteItem(
    transaccion: TransaccionPendiente,
    productos: List<ProductoReciclable>,  // Ahora recibe lista de productos
    onScanQR: () -> Unit = {}
) {
    var showQRPreview by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Encabezado con estado y botón QR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Transacción #${transaccion.idTransaccion.take(8)}",  // Muestra ID corto
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Estado: ${transaccion.estado}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = when (transaccion.estado) {
                            EstadoTransaccion.PENDIENTE -> MaterialTheme.colorScheme.primary
                            EstadoTransaccion.COMPLETADA -> MaterialTheme.colorScheme.tertiary
                            EstadoTransaccion.CANCELADA -> MaterialTheme.colorScheme.error
                        }
                    )
                }

                IconButton(onClick = { showQRPreview = true }) {
                    Icon(
                        imageVector = Icons.Outlined.QrCode,
                        contentDescription = "Ver QR",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Lista compacta de productos
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Productos (${productos.size})",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Lista compacta de productos con scroll horizontal
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(productos) { producto ->
                        ProductoChip(producto = producto)
                    }
                }
            }

            // Fecha y acciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarToday,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = " ${transaccion.fechaCreacion}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            if (transaccion.estado == EstadoTransaccion.PENDIENTE) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onScanQR,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.QrCodeScanner,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Escanear QR para completar")
                }
            }
        }
    }

    // Dialog para mostrar el QR (se mantiene igual)
    if (showQRPreview) {
        AlertDialog(
            onDismissRequest = { showQRPreview = false },
            title = {
                Text(
                    text = "Código QR de la Transacción",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .size(200.dp)
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        QRCode(
                            content = transaccion.codigoQR,
                            modifier = Modifier.fillMaxSize(),{}
                        )
                    }

                    Text(
                        text = "Muestra este código QR para validar la transacción",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showQRPreview = false }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

@Composable
fun ProductoChip(producto: ProductoReciclable) {
    Card(
        modifier = Modifier.width(120.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = producto.urlImagenProducto,
                contentDescription = producto.nombreProducto,
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.small)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = producto.nombreProducto,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "${producto.precio} ${producto.monedaDeCompra}",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}