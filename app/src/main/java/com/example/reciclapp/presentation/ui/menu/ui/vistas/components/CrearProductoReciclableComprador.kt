import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.reciclapp.domain.entities.Categoria
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.presentation.viewmodel.CompradoresViewModel
import com.example.reciclapp.presentation.viewmodel.VendedoresViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.Date
import java.util.UUID


private const val TAG = "AddItemCardVendedor"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearProductoReciclableComprador(
    compradoresViewModel: CompradoresViewModel,
    mainNavController: NavHostController
) {

    var selectedCategory by remember { mutableStateOf<Categoria?>(null) }
    var selectedProduct by remember { mutableStateOf<ProductoReciclable?>(null) }
    var productToUpdate by remember { mutableStateOf<ProductoReciclable?>(null) }
    var selectedUnidad by remember { mutableStateOf<String?>(null) }
    var precio by remember { mutableStateOf("") }
    var expandedCategory by remember { mutableStateOf(false) }
    var expandedProducts by remember { mutableStateOf(false) }
    var expandedUnidadDeMedida by remember { mutableStateOf(false) }
    var detalles by remember { mutableStateOf("") }
    val isLoading by compradoresViewModel.isLoading.observeAsState(false)
    val context = LocalContext.current
    var isUpdatingProduct by remember { mutableStateOf(false) }

    compradoresViewModel.obtenerProductosPredeterminados()

    val productosPredeterminados =
        compradoresViewModel.productosPredeterminados.collectAsState().value

    productToUpdate = compradoresViewModel.productToUpdate.collectAsState().value

    // Observar cambios en productToUpdate
    LaunchedEffect(productToUpdate) {
        if (productToUpdate != null) {
            selectedProduct = productToUpdate
            selectedCategory =
                ListOfCategorias.categorias.find { it.idCategoria == productToUpdate!!.idCategoria }
            selectedUnidad = productToUpdate!!.unidadMedida
            precio = productToUpdate!!.precio.toString()
            detalles = productToUpdate!!.detallesProducto
            isUpdatingProduct = true
        }
    }

    LaunchedEffect(Unit) {
        compradoresViewModel.showToast.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            mainNavController.popBackStack()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            compradoresViewModel.resetProductToUpdate()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Titulo()

                // Sección de Categoría y Producto
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Información del Producto",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )

                    // Categoría
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Categoría",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        ExposedDropdownMenuBox(
                            expanded = expandedCategory,
                            onExpandedChange = { expandedCategory = it }
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
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
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
                                            selectedProduct = null
                                            selectedUnidad = ""
                                            expandedCategory = false
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                        colors = MenuDefaults.itemColors(
                                            textColor = Color(
                                                0xFF2C2C2C
                                            )
                                        )
                                    )
                                }
                            }
                        }
                    }

                    // Producto
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Producto",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        ExposedDropdownMenuBox(
                            expanded = expandedProducts,
                            onExpandedChange = { expandedProducts = it }
                        ) {
                            OutlinedTextField(
                                value = selectedProduct?.nombreProducto ?: "Producto",
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
                                val productos =
                                    selectedCategory?.let { productosPredeterminados.filter { selectedCategory!!.idCategoria == it.idCategoria } }
                                        ?: productosPredeterminados

                                Log.d("Productos", "${productos.size}")

                                productos.forEach { producto ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = producto.nombreProducto,
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
                }

                // Sección de Precio y Unidad de Medida
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Detalles de Compra",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Unidad de medida (2/3 del espacio)
                        Column(
                            modifier = Modifier.weight(2f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Unidad de medida",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            ExposedDropdownMenuBox(
                                expanded = expandedUnidadDeMedida,
                                onExpandedChange = { expandedUnidadDeMedida = it }
                            ) {
                                OutlinedTextField(
                                    value = selectedUnidad ?: "",
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
                                        selectedCategory?.unidadDeMedida?.split(", ")
                                            ?: ListOfCategorias.categorias.flatMap {
                                                it.unidadDeMedida.split(
                                                    ", "
                                                )
                                            }.distinct()

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

                        // Precio (1/3 del espacio)
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Precio",
                                style = MaterialTheme.typography.bodyMedium
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
                    }
                }

                // Sección de Detalles
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Detalles Adicionales",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    OutlinedTextField(
                        value = detalles,
                        onValueChange = { detalles = it },
                        placeholder = { Text("Describe los detalles específicos del producto que buscas...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
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

                // Botón de acción
                Button(
                    onClick = {
                        val precioValue = precio.toDoubleOrNull() ?: 0.0

                        if (isUpdatingProduct) {
                            productToUpdate?.let { productToUpdate ->
                                compradoresViewModel.actualizarProducto(
                                    productToUpdate.copy(
                                        nombreProducto = selectedProduct?.nombreProducto ?: "",
                                        detallesProducto = detalles,
                                        precio = precioValue,
                                        fechaModificacion = Date().toString(),
                                        unidadMedida = selectedUnidad ?: "",
                                        idComprador = compradoresViewModel.myUser.value?.idUsuario
                                            ?: "0"
                                    )
                                )
                            }
                        } else {
                            selectedProduct?.let { baseProduct ->
                                compradoresViewModel.registrarNuevoProducto(
                                    baseProduct.copy(
                                        idProducto = UUID.randomUUID().toString(),
                                        detallesProducto = detalles,
                                        urlImagenProducto = "",
                                        precio = precioValue,
                                        fechaPublicacion = Date().toString(),
                                        fechaModificacion = Date().toString(),
                                        categoria = selectedCategory?.nombre ?: "",
                                        ubicacionProducto = compradoresViewModel.myUser.value?.direccion
                                            ?: "",
                                        monedaDeCompra = "Bs",
                                        unidadMedida = selectedUnidad ?: "",
                                        meGusta = 0,
                                        fueVendida = false,
                                        idComprador = compradoresViewModel.myUser.value?.idUsuario
                                            ?: "0"
                                    )
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (isUpdatingProduct)
                            "Actualizar Producto"
                        else
                            "Registrar Producto",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}


@Composable
private fun Titulo() {
    Text(
        text = "Nuevo Producto Reciclable que compras",
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