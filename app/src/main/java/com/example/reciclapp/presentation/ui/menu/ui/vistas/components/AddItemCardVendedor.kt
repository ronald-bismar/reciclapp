import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.reciclapp.domain.entities.Categoria
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.presentation.viewmodel.VendedoresViewModel
import com.example.reciclapp.util.ListOfCategorias
import java.util.Date
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemCardVendedor(
) {

    val vendedoresViewModelVendedores: VendedoresViewModel = hiltViewModel()

    var selectedCategory by remember { mutableStateOf<Categoria?>(null) }
    var selectedProduct by remember { mutableStateOf<String?>(null) }
    var cantidad by remember { mutableStateOf("") }
    var selectedUnidad by remember { mutableStateOf<String?>(null) }
    var precio by remember { mutableStateOf("") }
    var expandedCategory by remember { mutableStateOf(false) }
    var expandedProducts by remember { mutableStateOf(false) }
    var expandedUnidadDeMedida by remember { mutableStateOf(false) }
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var detalles by remember { mutableStateOf("") }
    var puntosCalculados by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        Titulo()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expandedCategory,
                onExpandedChange = { expandedCategory = it },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = selectedCategory?.nombre ?: "Categoria",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            Icons.Filled.KeyboardArrowDown,
                            contentDescription = "expandir",
                            modifier = Modifier.graphicsLayer {
                                rotationZ = if (expandedCategory) 180f else 0f
                            }
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.secondary),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium
                    )
                )

                DropdownMenu(
                    expanded = expandedCategory,
                    onDismissRequest = { expandedCategory = false },
                    modifier = Modifier
                        .exposedDropdownSize()
                        .background(Color.White)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    ListOfCategorias.categorias.forEach { categoria ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = categoria.nombre,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF2C2C2C)
                                )
                            },
                            onClick = {
                                selectedCategory = categoria
                                selectedProduct = ""
                                selectedUnidad = ""
                                expandedCategory = false
                                puntosCalculados = categoria.calcularPuntosTransaccion(categoria, cantidad.toDoubleOrNull() ?: 0.0)
                            },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            colors = MenuDefaults.itemColors(textColor = Color(0xFF2C2C2C))
                        )
                    }
                }
            }
            // Dropdown Producto
            ExposedDropdownMenuBox(
                expanded = expandedProducts,
                onExpandedChange = { expandedProducts = it },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = selectedProduct ?: "Producto",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            Icons.Filled.KeyboardArrowDown,
                            contentDescription = "expandir",
                            modifier = Modifier.graphicsLayer {
                                rotationZ = if (expandedProducts) 180f else 0f
                            }
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .background(Color.White),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFF2C2C2C),
                        fontSize = 18.sp
                    )
                )

                DropdownMenu(
                    expanded = expandedProducts,
                    onDismissRequest = { expandedProducts = false },
                    modifier = Modifier
                        .exposedDropdownSize()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    val productos = selectedCategory?.productosDeCategoria
                        ?: ListOfCategorias.categorias.flatMap { it.productosDeCategoria.map { producto -> producto } }

                    productos.forEach { producto ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = producto,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF2C2C2C)
                                )
                            },
                            onClick = {
                                selectedProduct = producto
                                selectedUnidad = ""
                                expandedProducts = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            colors = MenuDefaults.itemColors(
                                textColor = Color(0xFF2C2C2C)
                            )
                        )
                    }
                }
            }
        }

        TextoPuntosPorTransaccion(puntosCalculados)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Cantidad",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 4.dp),
                    fontWeight = FontWeight.Medium
                )
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = {
                        cantidad = it
                        puntosCalculados = selectedCategory?.calcularPuntosTransaccion(
                            selectedCategory!!, it.toDoubleOrNull() ?: 0.0
                        ) ?: 0
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                )

            }

            // Columna para "Unidad de medida" (3/4 del espacio)
            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = "Unidad de medida",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 4.dp),
                    fontWeight = FontWeight.Medium
                )
                ExposedDropdownMenuBox(
                    expanded = expandedUnidadDeMedida,
                    onExpandedChange = { expandedUnidadDeMedida = it }
                ) {
                    OutlinedTextField(
                        value = selectedUnidad?: "",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                Icons.Filled.KeyboardArrowDown,
                                contentDescription = "expandir"
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                            focusedBorderColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    )
                    DropdownMenu(
                        expanded = expandedUnidadDeMedida,
                        onDismissRequest = { expandedUnidadDeMedida = false },
                        modifier = Modifier
                            .exposedDropdownSize()
                            .background(
                                color = Color.White,
                            )
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        val unidadesDeMedida =
                            ListOfCategorias.categorias.find {
                                it.productosDeCategoria.contains(
                                    selectedProduct
                                )
                            }?.unidadDeMedida?.split(
                                ", "
                            )
                                ?: ListOfCategorias.categorias.flatMap { it.unidadDeMedida.split(", ") }

                        val unidadesSinRepetir = unidadesDeMedida.distinct()

                        unidadesSinRepetir.forEach { unidadDeMedida ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = unidadDeMedida,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF2C2C2C)
                                    )
                                },
                                onClick = {
                                    selectedUnidad = unidadDeMedida
                                    expandedUnidadDeMedida = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                colors = MenuDefaults.itemColors(
                                    textColor = Color(0xFF2C2C2C)
                                )
                            )
                        }
                    }
                }
            }
        }
        // Precio
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Precio",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 4.dp),
                fontWeight = FontWeight.Medium
            )
            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                prefix = { Text("Bs. ") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                )
            )
        }

        ImagePickerPanel(
            imageUris = imageUris,
            onImagesSelected = { imageUris = it }
        )

        // Detalles adicionales
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
        ) {
            OutlinedTextField(
                value = detalles,
                onValueChange = { detalles = it },
                placeholder = {
                    Text(
                        text = "Detalles adicionales",
                        color = Color.Gray,
                        fontSize = 18.sp // Tamaño del placeholder
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent, // Borde no enfocado transparente
                    focusedBorderColor = Color.Transparent,   // Borde enfocado transparente
                    disabledBorderColor = Color.Transparent,  // Borde deshabilitado transparente
                    errorBorderColor = Color.Transparent      // Borde de error transparente
                ),
                shape = RoundedCornerShape(8.dp),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 18.sp // Tamaño del texto ingresado
                )
            )
        }

        // Botón de publicar
        Button(
            onClick = {
                // Crear y enviar el producto
                val producto = ProductoReciclable(
                    idProducto = UUID.randomUUID().toString(),
                    nombreProducto = selectedProduct ?: "",
                    detallesProducto = detalles,
                    urlImagenProducto = imageUris.firstOrNull()?.toString() ?: "",
                    precio = precio.toDoubleOrNull() ?: 0.0,
                    fechaPublicacion = Date().toString(),
                    fechaModificacion = Date().toString(),
                    cantidad = cantidad.toIntOrNull() ?: 0,
                    categoria = selectedCategory?.nombre ?: "",
                    ubicacionProducto = vendedoresViewModelVendedores.selectedVendedor.value?.direccion?: "",
                    monedaDeCompra = "Bs",
                    unidadMedida = selectedUnidad ?: "",
                    puntosPorCompra = puntosCalculados,
                    meGusta = 0,
                    fueVendida = false,
                    idUsuario = vendedoresViewModelVendedores.selectedVendedor.value?.idUsuario?: "0",
                    idCategoria = selectedCategory?.idCategoria ?: "0"
                )

                vendedoresViewModelVendedores.registrarNuevoProducto(producto)
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

@Composable
private fun TextoPuntosPorTransaccion(puntos: Int) {
    Text(
        "$puntos por transacción!",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium,
        color = Color(0xFFF39A00),
        fontSize = 18.sp
    )
}


@Composable
private fun Titulo() {
    Text(
        text = "Producto reciclable a la venta",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Medium
    )
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
            .padding(vertical = 2.dp)
            .height(200.dp)
            .clickable {
                galleryLauncher.launch("image/*")
            },
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (imageUris.isEmpty()) {
                Icon(
                    imageVector = Icons.Outlined.Image,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
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
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddItemCardVendedorPreview() {
    AddItemCardVendedor()
}