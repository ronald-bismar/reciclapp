package com.example.reciclapp.presentation.ui.menu.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.reciclapp.R
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.presentation.viewmodel.VendedoresViewModel

@Composable
fun SocialMediaScreenVendedores(
    mainNavController: NavController,
    vendedoresViewModel: VendedoresViewModel = hiltViewModel()
) {

    // Evitar recargar los datos innecesariamente
    LaunchedEffect(Unit) {
        vendedoresViewModel.fetchAllProducts()
    }

    val productos by vendedoresViewModel.productos.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color.LightGray)
    ) {
        if (productos.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productos) { producto ->
                    CardSocialMediaReciclador(productoReciclable = producto, mainNavController)
                }
            }
        }
    }
}

@Composable
fun CardSocialMediaReciclador(
    productoReciclable: ProductoReciclable, mainNavController: NavController,
    vendedoresViewModel: VendedoresViewModel = hiltViewModel()
) {
    var isLiked by remember { mutableStateOf(false) }
    var countLikes by remember { mutableIntStateOf(productoReciclable.meGusta) }
    val precio: String = if (productoReciclable.precio < 1) "${productoReciclable.precio}0"
    else productoReciclable.precio.toInt().toString()
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Vendo ${productoReciclable.nombreProducto} para reciclar",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
            Text(
                text = "Cantidad: ${productoReciclable.cantidad} ${productoReciclable.unidadMedida}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = "Puntos por material: ${productoReciclable.puntosPorCompra}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = productoReciclable.descripcionProducto,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Text(
                text = precio,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            AsyncImage(
                model = productoReciclable.urlImagenProducto,
                contentDescription = "Producto vendido",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.img_reciclando2) // Imagen en caso de error
            )
            Text(
                text = "$countLikes Me gusta",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            HorizontalDivider()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                IconButton(onClick = {
                    isLiked = !isLiked
                    vendedoresViewModel.updateLikedProducto(productoReciclable, isLiked)
                    countLikes = if (isLiked) countLikes + 1 else countLikes - 1
                }, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "FavoriteBorder",
                        tint = if (isLiked) Color.Red else Color.Black
                    )
                }
                IconButton(onClick = {
                    val profileRoute =
                        "vendedorPerfil/${productoReciclable.idUsuario}" //vamos a pantalla perfil del vendedor

                    mainNavController.navigate(profileRoute)
                }, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Contact",
                        tint = Color.Black
                    )
                }
                IconButton(onClick = { }, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

