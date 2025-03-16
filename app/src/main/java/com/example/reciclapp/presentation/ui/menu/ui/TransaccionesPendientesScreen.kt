package com.example.reciclapp.presentation.ui.menu.ui

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.reciclapp.domain.entities.EstadoTransaccion
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.TransaccionPendiente
import com.example.reciclapp.presentation.viewmodel.TransaccionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransaccionesPendientesScreen(
    viewModel: TransaccionViewModel = hiltViewModel(),
    navController: NavController
) {
    val transacciones by viewModel.transaccionesPendientes.collectAsState()
    val productos by viewModel.productos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarTransaccionesPendientes()
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
                    TransaccionPendienteItem(
                        transaccion = transaccion,
                        producto = productos.find { it.idProducto == transaccion.idProducto },
                        onScanQR = {
                            navController.navigate("qr-scanner")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TransaccionPendienteItem(
    transaccion: TransaccionPendiente,
    producto: ProductoReciclable?,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = producto?.nombreProducto ?: "Producto no disponible",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Estado: ${transaccion.estado}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                // Botón para mostrar QR
                IconButton(onClick = { showQRPreview = true }) {
                    Icon(
                        imageVector = Icons.Outlined.QrCode,
                        contentDescription = "Ver QR",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Fecha de la transacción
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarToday,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = " ${transaccion.fechaCreacion}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (transaccion.estado == EstadoTransaccion.PENDIENTE) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onScanQR,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.QrCodeScanner,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Escanear QR")
                }
            }
        }
    }

    // Dialog para mostrar el QR
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