import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.reciclapp.domain.entities.Categoria
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.util.ListOfCategorias

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemCardVendedor(
    onSubmit: (ProductoReciclable) -> Unit,
    modifier: Modifier = Modifier
) {
    var cantidad by remember { mutableStateOf("20") }
    var precio by remember { mutableStateOf("10") }
    var selectedCategory by remember { mutableStateOf<Categoria?>(null) }
    var selectedProduct by remember { mutableStateOf<String?>(null) }
    var expandedCategory by remember { mutableStateOf(false) }
    var expandedUnidad by remember { mutableStateOf(false) }
    var selectedUnidad by remember { mutableStateOf("Kilogramos (Kg)") }
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var showPointsInfo by remember { mutableStateOf(false) }
    var detalles by remember { mutableStateOf("") }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Título
            Text(
                text = "Producto reciclable a la venta",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // Puntos por transacción
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "30",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFFFB74D)
                    )
                    Text(
                        " puntos por transacción!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Categoría y Producto
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Dropdown Categoría
                ExposedDropdownMenuBox(
                    expanded = expandedCategory,
                    onExpandedChange = { expandedCategory = it },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = selectedCategory?.nombre ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Categoría") },
                        trailingIcon = { Icon(Icons.Filled.KeyboardArrowDown, "expandir") },
                        modifier = Modifier.menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    DropdownMenu(
                        expanded = expandedCategory,
                        onDismissRequest = { expandedCategory = false }
                    ) {
                        ListOfCategorias.categorias.forEach { categoria ->
                            DropdownMenuItem(
                                text = { Text(categoria.nombre) },
                                onClick = {
                                    selectedCategory = categoria
                                    expandedCategory = false
                                }
                            )
                        }
                    }
                }

                // Dropdown Producto
                if (selectedCategory != null) {
                    ExposedDropdownMenuBox(
                        expanded = expandedUnidad,
                        onExpandedChange = { expandedUnidad = it },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = selectedProduct ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Producto") },
                            trailingIcon = { Icon(Icons.Filled.KeyboardArrowDown, "expandir") },
                            modifier = Modifier.menuAnchor(),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.primary
                            )
                        )

                        DropdownMenu(
                            expanded = expandedUnidad,
                            onDismissRequest = { expandedUnidad = false }
                        ) {
                            selectedCategory!!.productosDeCategoria.forEach { producto ->
                                DropdownMenuItem(
                                    text = { Text(producto) },
                                    onClick = {
                                        selectedProduct = producto
                                        expandedUnidad = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Cantidad y Unidad de medida
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                ExposedDropdownMenuBox(
                    expanded = expandedUnidad,
                    onExpandedChange = { expandedUnidad = it },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = selectedUnidad,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Unidad de medida") },
                        trailingIcon = { Icon(Icons.Filled.KeyboardArrowDown, "expandir") },
                        modifier = Modifier.menuAnchor()
                    )

                    DropdownMenu(
                        expanded = expandedUnidad,
                        onDismissRequest = { expandedUnidad = false }
                    ) {
                        listOf("Kilogramos (Kg)", "Gramos (g)", "Toneladas (T)").forEach { unidad ->
                            DropdownMenuItem(
                                text = { Text(unidad) },
                                onClick = {
                                    selectedUnidad = unidad
                                    expandedUnidad = false
                                }
                            )
                        }
                    }
                }
            }

            // Precio
            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                prefix = { Text("Bs. ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Puntos por compra
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "Puntos por compra",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        "10 puntos",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    TextButton(
                        onClick = { showPointsInfo = !showPointsInfo }
                    ) {
                        Text("¿En qué consiste los puntos por compra?")
                    }
                }
            }

            // Panel de imágenes
            ImagePickerPanel(
                imageUris = imageUris,
                onImagesSelected = { imageUris = it }
            )

            // Detalles adicionales
            OutlinedTextField(
                value = detalles,
                onValueChange = { detalles = it },
                label = { Text("Algún detalle que quieras adicionar?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                minLines = 3
            )

            // Botón de publicar
            Button(
                onClick = {
                    // Crear y enviar el producto
                    val producto = ProductoReciclable(
                        nombreProducto = selectedProduct ?: "",
                        descripcionProducto = detalles,
                        precio = precio.toDoubleOrNull() ?: 0.0,
                        cantidad = cantidad.toIntOrNull() ?: 0,
                        categoria = selectedCategory?.nombre ?: "",
                        urlImagenProducto = imageUris.firstOrNull()?.toString() ?: ""
                    )
                    onSubmit(producto)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Publicar material")
            }
        }
    }
}

@Composable
private fun ImagePickerPanel(
    imageUris: List<Uri>,
    onImagesSelected: (List<Uri>) -> Unit,
    modifier: Modifier = Modifier
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        onImagesSelected(uris.take(3))
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(200.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imageUris.isEmpty()) {
                Icon(
                    imageVector = Icons.Outlined.Image,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Toca para agregar fotos",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(imageUris) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier
                                .size(160.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            TextButton(
                onClick = { galleryLauncher.launch("image/*") }
            ) {
                Text(if (imageUris.isEmpty()) "Agregar fotos" else "Cambiar fotos")
            }
        }
    }
}