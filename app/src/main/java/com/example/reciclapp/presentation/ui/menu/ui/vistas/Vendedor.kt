package com.example.reciclapp.presentation.ui.menu.ui.vistas

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.Recycling
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.reciclapp.R
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.presentation.viewmodel.MensajeViewModel
import com.example.reciclapp.presentation.viewmodel.TransaccionViewModel
import com.example.reciclapp.presentation.viewmodel.VendedoresViewModel

private const val TAG = "Vendedor"

@SuppressLint("NewApi")
@Composable
fun Vendedor(
    mainNavController: NavHostController,
    vendedorId: String,
    vendedoresViewModel: VendedoresViewModel,
    transaccionViewModel: TransaccionViewModel,
    mensajeViewModel: MensajeViewModel
) {
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
            ActionButtonsVendedor(
                {
                    transaccionViewModel.setUserContacted(selectedVendedor)
                    mensajeViewModel.setUserContacted(selectedVendedor)
                    mainNavController.navigate("ScreenProductsForSaleVendedor")
                }
            )

            // Listado de productos a la venta
            ProfileSection2("Productos a la venta")
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
            .padding(horizontal = 6.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen del producto
        AsyncImage(
            model = item.urlImagenProducto,
            contentDescription = "Producto reciclable",
            modifier = Modifier
                .width(120.dp)
                .height(120.dp),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.icono_defecto)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = item.nombreProducto, style = MaterialTheme.typography.titleMedium)
            Text(text = item.detallesProducto, style = MaterialTheme.typography.bodyMedium)
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
                } else Color.Green
            )
        }
    }
}

@Composable
fun ActionButtonsVendedor(onContactoVendedorSelected: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        ActionButton3("Contactar por productos", Icons.Default.Recycling) {
            onContactoVendedorSelected()
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    HorizontalDivider()
}
