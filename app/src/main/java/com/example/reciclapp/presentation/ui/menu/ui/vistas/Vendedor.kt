package com.example.reciclapp.presentation.ui.menu.ui.vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.presentation.viewmodel.VendedoresViewModel

@Composable
fun Vendedor(
    navController: NavController,
    vendedorId: Int,
    vendedoresViewModel: VendedoresViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(vendedorId) {
        vendedoresViewModel.fetchVendedorById(vendedorId)
        vendedoresViewModel.fetchProductosByVendedor(vendedorId)
    }

    val selectedVendedor = vendedoresViewModel.selectedVendedor.collectAsState().value
    val productos = vendedoresViewModel.productos.collectAsState().value

    if (selectedVendedor != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProfilePicture2(
                        rememberAsyncImagePainter(model = selectedVendedor.urlImagenPerfil),
                        120.dp
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            ProfileSection2("Account Details")
            ProfileItem2("Name", selectedVendedor.nombre)
            ProfileItem2("LastName", selectedVendedor.apellido)
            ProfileItem2("Phone", selectedVendedor.telefono.toString())
            ProfileItem2("Address", selectedVendedor.direccion)
            ProfileItem2("Email", selectedVendedor.correo)
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(10.dp))
            // Botones de acciones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton3(
                    "Mensaje",
                    Icons.Default.Email
                ) {
                    openWhatsAppMessage(
                        context = context,
                        phoneNumber = "${selectedVendedor.telefono}"
                    )
                } // Ajusta el formato del número

                ActionButton3(
                    "Llamar",
                    Icons.Default.Call,
                ) {
                    initiateCall(
                        context = context,
                        phoneNumber = "${selectedVendedor.telefono}"
                    )
                } // Ajusta el formato del número
            }
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider()

            // Listado de objetos vendidos
            ProfileSection2("Objetos a la venta")
            SoldItemsList(productos)
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ProfilePicture2(painter: Painter, size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(size)
        )
    }
}

@Composable
fun ProfileSection2(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun ProfileItem2(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Text(text = value, fontSize = 16.sp)
    }
}

@Composable
fun SoldItemsList(soldItems: List<ProductoReciclable>) {
    Column {
        soldItems.forEach { item ->
            SoldItemCard(item)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SoldItemCard(item: ProductoReciclable) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal= 6.dp, vertical = 20.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = item.urlImagenProducto),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = item.nombreProducto, style = MaterialTheme.typography.titleMedium)
            Text(text = item.descripcionProducto, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "Precio: ${item.monedaDeCompra} ${item.precio}${if (item.precio < 1) '0' else ' '}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Cantidad: ${item.cantidad} ${item.unidadMedida}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(text = "Categoria: ${item.categoria}", style = MaterialTheme.typography.bodySmall)
            Text(
                text = if (item.fueVendida) "Estado: Vendido" else "Estado: Disponible",
                style = MaterialTheme.typography.bodySmall,
                color = if (item.fueVendida) {
                    if (isSystemInDarkTheme())
                        Color(0xFFFF6B6B)
                    else
                        Color.Red
                }
                    else Color.Green
            )
        }
    }
}
