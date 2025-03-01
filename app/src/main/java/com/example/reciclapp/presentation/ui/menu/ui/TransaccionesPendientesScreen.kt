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
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transacciones Pendientes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
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
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = producto?.nombreProducto ?: "Producto no encontrado",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = when(transaccion.estado) {
                        EstadoTransaccion.PENDIENTE -> "Pendiente"
                        EstadoTransaccion.COMPLETADA -> "Completada"
                        EstadoTransaccion.CANCELADA -> "Cancelada"
                    },
                    color = when(transaccion.estado) {
                        EstadoTransaccion.PENDIENTE -> Color(0xFFF39A00)
                        EstadoTransaccion.COMPLETADA -> Color(0xFF4CAF50)
                        EstadoTransaccion.CANCELADA -> Color.Red
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

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
}