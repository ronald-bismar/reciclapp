package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.content.myproducts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.presentation.viewmodel.CompradoresViewModel

private const val TAG = "MyPurchasesScreen"

@Composable
fun MyProductsToBuyScreen(
    compradoresViewModel: CompradoresViewModel,
) {
    val context = LocalContext.current
    val productosComprados = compradoresViewModel.productos.collectAsState().value
    val isLoading = compradoresViewModel.isLoading.value

    LaunchedEffect(Unit) {
        compradoresViewModel.myUser.value?.idUsuario?.let { userId ->
            compradoresViewModel.fetchProductosByComprador(userId)
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Mis Materiales",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Materiales reciclables que buscas comprar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(paddingValues)
        ) {
            when {
                isLoading == true -> LoadingState()
                productosComprados.isEmpty() -> EmptyState()
                else -> ProductList(productosComprados)
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.ShoppingBag,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 16.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        )
        Text(
            text = "¡Comienza tu lista de materiales!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Agrega los materiales reciclables que buscas comprar regularmente",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@Composable
private fun ProductList(productos: List<ProductoReciclable>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(productos) { producto ->
            TarjetaProductoMejorada(producto)
        }
    }
}

@Composable
private fun TarjetaProductoMejorada(producto: ProductoReciclable) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { /* Acción al hacer clic */ },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Encabezado con nombre y categoría
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = producto.nombreProducto,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                ProductoEtiqueta(
                    texto = producto.categoria,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Detalles del producto
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Cantidad y unidad
                DetalleItem(
                    icon = Icons.Outlined.Scale,
                    text = "${producto.cantidad} ${producto.unidadMedida}"
                )
                
                // Puntos
                DetalleItem(
                    icon = Icons.Outlined.EmojiEvents,
                    text = "${producto.puntosPorCompra} pts",
                    color = MaterialTheme.colorScheme.secondary
                )
                
                // Precio
                Text(
                    text = "${producto.precio} Bs",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun DetalleItem(
    icon: ImageVector,
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyProductsToBuyScreenPreview(){
    val botellaPlastica = ProductoReciclable(
        idProducto = "1",
        nombreProducto = "Botella plástica PET",
        detallesProducto = "Botellas plásticas de agua y refresco, limpias y comprimidas.",
        urlImagenProducto = "https://example.com/botella-plastica.jpg",
        precio = 0.5, // Precio por kilo
        fechaPublicacion = "2025-02-25",
        fechaModificacion = "2025-02-25",
        cantidad = 100,
        categoria = "Plástico",
        ubicacionProducto = "La Paz, Bolivia",
        monedaDeCompra = "Bs",
        unidadMedida = "kg",
        puntosPorCompra = 20,
        meGusta = 15,
        fueVendida = false,
        idVendedor = "12345",
        idComprador = null,
        idCategoria = "PLASTICO01",
        emisionCO2Kilo = 2.0,
        pesoPorUnidad = 0.02 // 20 gramos por botella
    )
    TarjetaProductoMejorada(botellaPlastica)
}