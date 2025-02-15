package com.example.reciclapp.presentation.ui.menu.ui.content.myproducts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.reciclapp.domain.entities.ProductoReciclable

@Composable
fun MyProductsScreen(mainNavController: NavHostController) {


    var showStats by remember { mutableStateOf(true) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mainNavController.navigate("AñadirProductoReciclable") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, "Agregar producto")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Encabezado
                item {
                    Text(
                        text = "Mis Productos 🌱",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Estadísticas
                item {
                    EstadisticasCard(showStats = showStats, onToggle = { showStats = !showStats })
                }

                // Tip del día
                item {
                    DailyTip()
                }

                // Lista de productos
                items(productosEjemplo) { producto ->
                    TarjetaProducto(producto)
                }
            }
        }
    }
}

@Composable
fun EstadisticasCard(showStats: Boolean, onToggle: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onToggle),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.EmojiEvents,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Mi Impacto Ambiental",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(
                    imageVector = if (showStats) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (showStats) "Ocultar" else "Mostrar"
                )
            }

            AnimatedVisibility(
                visible = showStats,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        EstadisticaItem(
                            icon = Icons.Outlined.Inventory,
                            titulo = "Productos Activos",
                            valor = "8",
                            tendencia = "+2 esta semana",
                            modifier = Modifier.weight(1f)
                        )
                        EstadisticaItem(
                            icon = Icons.Outlined.Park,
                            titulo = "CO₂ Evitado",
                            valor = "125 kg",
                            tendencia = "+15kg este mes",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        EstadisticaItem(
                            icon = Icons.Outlined.ThumbUp,
                            titulo = "Me Gusta",
                            valor = "39",
                            tendencia = "+5 hoy",
                            modifier = Modifier.weight(1f)
                        )
                        EstadisticaItem(
                            icon = Icons.Outlined.EmojiEvents,
                            titulo = "Nivel",
                            valor = "Reciclador Pro",
                            tendencia = "¡Cerca del siguiente!",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EstadisticaItem(
    icon: ImageVector,
    titulo: String,
    valor: String,
    tendencia: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = valor,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = tendencia,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
fun DailyTip(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        color = Color(0xFFB7D2FD),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon Container
            Surface(
                shape = CircleShape,
                color = Color(0xFF458DFF),
                modifier = Modifier.size(40.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lightbulb,
                        contentDescription = "Tip",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Text Content
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "¡Tip del día! 💡",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "¿Sabías que compactar tus botellas PET puede aumentar hasta un 40% las probabilidades de venta? ¡Inténtalo!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarjetaProducto(producto: ProductoReciclable) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Imagen del producto
                Image(
                    painter = rememberAsyncImagePainter(producto.urlImagenProducto),
                    contentDescription = producto.nombreProducto,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = producto.nombreProducto,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = producto.detallesProducto,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Row {
                            IconButton(onClick = { /* Editar */ }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Editar",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            IconButton(onClick = { /* Eliminar */ }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }

                    // Detalles con iconos
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ProductoDetalle(
                            icon = Icons.Outlined.Scale,
                            texto = "${producto.cantidad} ${producto.unidadMedida}"
                        )
                        ProductoDetalle(
                            icon = Icons.Outlined.LocationOn,
                            texto = producto.ubicacionProducto
                        )
                        ProductoDetalle(
                            icon = Icons.Outlined.CalendarToday,
                            texto = producto.fechaPublicacion
                        )
                        ProductoDetalle(
                            icon = Icons.Outlined.Favorite,
                            texto = "${producto.meGusta}"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Etiquetas y precio
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ProductoEtiqueta(
                        texto = producto.categoria,
                        color = MaterialTheme.colorScheme.primary
                    )
                    ProductoEtiqueta(
                        texto = "${producto.puntosPorCompra} puntos",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Text(
                    text = "${producto.precio} Bs",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

@Composable
fun ProductoDetalle(
    icon: ImageVector,
    texto: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = texto,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ProductoEtiqueta(
    texto: String,
    color: Color
) {
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = texto,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = color,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

// Datos de ejemplo
private val productosEjemplo = listOf(
    ProductoReciclable(
        idProducto = "1",
        nombreProducto = "Botellas PET",
        detallesProducto = "Botellas de plástico limpias y compactadas",
        urlImagenProducto = "https://example.com/bottle.jpg",
        precio = 25.50,
        fechaPublicacion = "14/02/2025",
        cantidad = 100,
        categoria = "Plástico",
        ubicacionProducto = "Zona Norte",
        unidadMedida = "kg",
        puntosPorCompra = 50,
        meGusta = 24
    ),
    ProductoReciclable(
        idProducto = "2",
        nombreProducto = "Cartón Corrugado",
        detallesProducto = "Cartón limpio y sin humedad, ideal para reciclaje",
        urlImagenProducto = "https://example.com/carton.jpg",
        precio = 15.75,
        fechaPublicacion = "10/03/2025",
        cantidad = 200,
        categoria = "Papel y Cartón",
        ubicacionProducto = "Zona Centro",
        unidadMedida = "kg",
        puntosPorCompra = 30,
        meGusta = 18
    ),
    ProductoReciclable(
        idProducto = "3",
        nombreProducto = "Latas de Aluminio",
        detallesProducto = "Latas limpias y aplastadas, listas para reciclar",
        urlImagenProducto = "https://example.com/latas.jpg",
        precio = 40.00,
        fechaPublicacion = "05/04/2025",
        cantidad = 150,
        categoria = "Metal",
        ubicacionProducto = "Zona Sur",
        unidadMedida = "kg",
        puntosPorCompra = 60,
        meGusta = 35
    ),
    ProductoReciclable(
        idProducto = "4",
        nombreProducto = "Vidrio Transparente",
        detallesProducto = "Botellas y frascos de vidrio transparente, sin tapas",
        urlImagenProducto = "https://example.com/vidrio.jpg",
        precio = 12.00,
        fechaPublicacion = "20/01/2025",
        cantidad = 80,
        categoria = "Vidrio",
        ubicacionProducto = "Zona Este",
        unidadMedida = "kg",
        puntosPorCompra = 20,
        meGusta = 12
    ),
    ProductoReciclable(
        idProducto = "5",
        nombreProducto = "Baterías Usadas",
        detallesProducto = "Baterías de litio y níquel-cadmio, recogida especializada",
        urlImagenProducto = "https://example.com/baterias.jpg",
        precio = 5.00,
        fechaPublicacion = "25/05/2025",
        cantidad = 50,
        categoria = "Electrónicos",
        ubicacionProducto = "Zona Oeste",
        unidadMedida = "unidad",
        puntosPorCompra = 100,
        meGusta = 8
    ),
    ProductoReciclable(
        idProducto = "6",
        nombreProducto = "Tetra Pak",
        detallesProducto = "Envases de Tetra Pak limpios y compactados",
        urlImagenProducto = "https://example.com/tetrapak.jpg",
        precio = 18.00,
        fechaPublicacion = "30/06/2025",
        cantidad = 120,
        categoria = "Envases Mixtos",
        ubicacionProducto = "Zona Norte",
        unidadMedida = "kg",
        puntosPorCompra = 40,
        meGusta = 22
    ),
    ProductoReciclable(
        idProducto = "7",
        nombreProducto = "Pilas Alcalinas",
        detallesProducto = "Pilas usadas, recogida para reciclaje seguro",
        urlImagenProducto = "https://example.com/pilas.jpg",
        precio = 3.50,
        fechaPublicacion = "15/07/2025",
        cantidad = 300,
        categoria = "Electrónicos",
        ubicacionProducto = "Zona Centro",
        unidadMedida = "unidad",
        puntosPorCompra = 15,
        meGusta = 10
    ),
    ProductoReciclable(
        idProducto = "8",
        nombreProducto = "Madera Recuperada",
        detallesProducto = "Madera limpia y en buen estado, ideal para reutilización",
        urlImagenProducto = "https://example.com/madera.jpg",
        precio = 30.00,
        fechaPublicacion = "10/08/2025",
        cantidad = 70,
        categoria = "Madera",
        ubicacionProducto = "Zona Sur",
        unidadMedida = "kg",
        puntosPorCompra = 25,
        meGusta = 14
    )
)