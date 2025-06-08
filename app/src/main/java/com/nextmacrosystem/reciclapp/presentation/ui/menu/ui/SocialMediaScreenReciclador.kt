<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/ui/menu/ui/SocialMediaScreenReciclador.kt
package com.example.reciclapp_bolivia.presentation.ui.menu.ui
========
package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/ui/menu/ui/SocialMediaScreenReciclador.kt

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.Construction
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Recycling
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Weekend
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/ui/menu/ui/SocialMediaScreenReciclador.kt
import com.example.reciclapp_bolivia.R
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.presentation.viewmodel.VendedoresViewModel
========
import com.nextmacrosystem.reciclapp.R
import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.presentation.viewmodel.VendedoresViewModel
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/ui/menu/ui/SocialMediaScreenReciclador.kt

private const val TAG = "SocialMediaScreenReciclador"

@Composable
fun SocialMediaScreenVendedores(
    vendedoresViewModel: VendedoresViewModel,
    mainNavController: NavController,
) {
    val productosConVendedores by vendedoresViewModel.productosConVendedores.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color.LightGray.copy(
                    alpha = 0.5f
                )
            )
    ) {
        if (productosConVendedores.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                EmptyProductsMessage()
            }

        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productosConVendedores) { (producto, vendedor) ->
                    CardSocialMediaReciclador(producto, vendedor, mainNavController)
                }
            }
        }
    }
}

@Composable
fun EmptyProductsMessage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "No hay productos",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No hay productos disponibles",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Por favor intenta más tarde.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CardSocialMediaReciclador(
    productoReciclable: ProductoReciclable,
    vendedor: Usuario,
    mainNavController: NavController,
    modifier: Modifier = Modifier,
    vendedoresViewModel: VendedoresViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isLiked by remember { mutableStateOf(false) }
    var countLikes by remember { mutableIntStateOf(productoReciclable.meGusta) }

    // Calcular impacto ambiental basado en los datos del producto
    val emisiones =
        productoReciclable.emisionCO2Kilo * productoReciclable.pesoPorUnidad * productoReciclable.cantidad
    val impactoTexto = when {
        productoReciclable.categoria.contains("Plástico", ignoreCase = true) ->
            "Evitaste ${String.format("%.1f", emisiones)}kg de CO₂ al ambiente"

        productoReciclable.categoria.contains("Papel", ignoreCase = true) ->
            "Salvaste ${productoReciclable.cantidad} árboles de ser talados"

        productoReciclable.categoria.contains("Electrónicos", ignoreCase = true) ->
            "Recuperaste ${
                String.format(
                    "%.1f",
                    productoReciclable.pesoPorUnidad * productoReciclable.cantidad
                )
            }kg de materiales valiosos"

        productoReciclable.categoria.contains("Mueble", ignoreCase = true) ->
            "Salvaste ${
                String.format(
                    "%.1f",
                    productoReciclable.pesoPorUnidad * productoReciclable.cantidad
                )
            }kg de madera de ir al vertedero"

        productoReciclable.categoria.contains("Ropa", ignoreCase = true) ->
            "Ahorraste ${String.format("%.1f", emisiones)}L de agua al medio ambiente"

        productoReciclable.categoria.contains("Metal", ignoreCase = true) ->
            "Ahorraste ${String.format("%.1f", emisiones)}kg de CO₂ en minería"

        else ->
            "Tu compra contribuye a reducir ${String.format("%.1f", emisiones)}kg de residuos"
    }

    val precio: String = if (productoReciclable.precio < 1) "${productoReciclable.precio}0"
    else productoReciclable.precio.toInt().toString()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // Badge de categoría
            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
                    .zIndex(1f),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = when {
                            productoReciclable.categoria.contains(
                                "Plástico",
                                ignoreCase = true
                            ) -> Icons.Outlined.ShoppingBag

                            productoReciclable.categoria.contains(
                                "Papel",
                                ignoreCase = true
                            ) -> Icons.Outlined.Description

                            productoReciclable.categoria.contains(
                                "Electrónicos",
                                ignoreCase = true
                            ) -> Icons.Outlined.PhoneAndroid

                            productoReciclable.categoria.contains(
                                "Mueble",
                                ignoreCase = true
                            ) -> Icons.Outlined.Weekend

                            productoReciclable.categoria.contains(
                                "Ropa",
                                ignoreCase = true
                            ) -> Icons.Outlined.Checkroom

                            productoReciclable.categoria.contains(
                                "Metal",
                                ignoreCase = true
                            ) -> Icons.Outlined.Construction

                            else -> Icons.Outlined.Category
                        },
                        contentDescription = "Categoría",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = productoReciclable.categoria.ifEmpty { "Reciclable" },
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Column {
                // Imagen del producto
                AsyncImage(
                    model = productoReciclable.urlImagenProducto,
                    contentDescription = "Producto reciclable",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.icono_defecto)
                )

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Título y tipo de material
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Recycling,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = productoReciclable.nombreProducto,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = "Vendo ${productoReciclable.nombreProducto} para reciclar",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        // Cantidad y unidad de medida
                        Text(
                            text = "Cantidad: ${productoReciclable.cantidad} ${productoReciclable.unidadMedida}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = "$precio ${productoReciclable.monedaDeCompra}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // EcoPuntos
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "EcoPuntos",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "+${productoReciclable.puntosPorCompra} EcoPuntos al comprar",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Información del vendedor con foto de perfil
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Foto de perfil redonda
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(vendedor.urlImagenPerfil.ifEmpty { R.drawable.perfil })
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Foto de perfil de ${vendedor.nombre}",
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop,
                                error = painterResource(R.drawable.perfil),
                                placeholder = painterResource(R.drawable.perfil)
                            )
                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = vendedor.nombre,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = vendedor.apellido,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Iconos de acción
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = countLikes.toString(),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                IconButton(
                                    onClick = {
                                        isLiked = !isLiked
                                        vendedoresViewModel.updateLikedProducto(
                                            productoReciclable,
                                            isLiked
                                        )
                                        countLikes = if (isLiked) countLikes + 1 else countLikes - 1
                                        Toast.makeText(context, if (isLiked) "Me interesa" else "No me interesa", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Me gusta",
                                        tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            // Contactar
                            IconButton(
                                onClick = {
                                    mainNavController.navigate("VendedorPerfil/${productoReciclable.idVendedor}")
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Phone,
                                    contentDescription = "Contactar",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

//                            // Ubicación
//                            IconButton(
//                                onClick = { /* Acción para ver ubicación */ },
//                                modifier = Modifier.size(32.dp)
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Outlined.LocationOn,
//                                    contentDescription = "Ubicación",
//                                    tint = MaterialTheme.colorScheme.onSurface,
//                                    modifier = Modifier.size(20.dp)
//                                )
//                            }

                            // Compartir
                            IconButton(
                                onClick = { compartirProductoConImagen(context, productoReciclable) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Share,
                                    contentDescription = "Compartir",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun compartirProductoConImagen(context: Context, producto: ProductoReciclable) {
    val textoCompartir = buildString {
        append("🌱 ¡Producto Reciclable Disponible! 🌱\n\n")
        append("📦 ${producto.nombreProducto}\n")
        append("💰 Precio: ${producto.monedaDeCompra} ${producto.precio} por ${producto.unidadMedida}\n")
        append("📍 Ubicación: ${producto.ubicacionProducto}\n")
        append("📊 Cantidad: ${producto.cantidad} ${producto.unidadMedida}\n")
        append("🏷️ Categoría: ${producto.categoria}\n")

        if (producto.detallesProducto.isNotEmpty()) {
            append("📝 Detalles: ${producto.detallesProducto}\n")
        }

        if (producto.urlImagenProducto.isNotEmpty()) {
            append("🖼️ Ver imagen: ${producto.urlImagenProducto}\n")
        }

        append("\n#Reciclaje #EcoFriendly #Bolivia #ReciclaAppBolivia")
    }

    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, textoCompartir)
        putExtra(Intent.EXTRA_SUBJECT, "Producto Reciclable: ${producto.nombreProducto}")
    }

    context.startActivity(Intent.createChooser(intent, "Compartir producto"))
}