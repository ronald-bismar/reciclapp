package com.example.reciclapp.presentation.ui.menu.ui.vistas.components

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.reciclapp.domain.entities.Categoria
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.util.ListOfCategorias
import java.io.IOException

@Composable
fun AddItemCardVendedor(onSubmit: (ProductoReciclable) -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Categoria?>(null) }
    var selectedProduct by remember { mutableStateOf<String?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val context = LocalContext.current

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
                imageBitmap = loadBitmapFromUri(context, it)
            }
        }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(4.dp, shape = RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Título del formulario
            Text(
                text = "Publicar Producto Reciclable",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Campo: Nombre del producto
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre del Producto") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo: Descripción del producto
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown: Selección de categoría
            var expandedCategory by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = { expandedCategory = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(selectedCategory?.nombre ?: "Seleccionar Categoría")
                }
                DropdownMenu(
                    expanded = expandedCategory,
                    onDismissRequest = { expandedCategory = false }
                ) {
                    ListOfCategorias.categorias.forEach { categoria ->
                        DropdownMenuItem(
                            onClick = {
                                selectedCategory = categoria
                                expandedCategory = false
                                selectedProduct = null // Resetear producto al cambiar categoría
                            }
                        ) {
                            Text(categoria.nombre)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown: Selección de producto (dependiente de la categoría)
            var expandedProduct by remember { mutableStateOf(false) }
            if (selectedCategory != null) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        onClick = { expandedProduct = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(selectedProduct ?: "Seleccionar Producto")
                    }
                    DropdownMenu(
                        expanded = expandedProduct,
                        onDismissRequest = { expandedProduct = false }
                    ) {
                        selectedCategory?.productosDeCategoria?.forEach { producto ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedProduct = producto
                                    expandedProduct = false
                                }
                            ) {
                                Text(producto)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Campo: Precio
            TextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Precio (Bs)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo: Cantidad
            TextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo: Ubicación
            TextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Ubicación") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Botón: Seleccionar imagen
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text("Seleccionar Imagen")
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar imagen seleccionada
            imageBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            // Botón: Enviar formulario
            Button(
                onClick = {
                    val newProduct = ProductoReciclable(
                        nombreProducto = name,
                        descripcionProducto = description,
                        precio = price.toDoubleOrNull() ?: 0.0,
                        cantidad = quantity.toIntOrNull() ?: 0,
                        ubicacionProducto = location,
                        categoria = selectedCategory?.nombre ?: "",
                        urlImagenProducto = imageUri.toString()
                    )
                    onSubmit(newProduct)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                enabled = name.isNotEmpty() && description.isNotEmpty() && selectedCategory != null
            ) {
                Text("Publicar Producto")
            }
        }
    }
}

private fun loadBitmapFromUri(context: Context, uri: Uri): ImageBitmap? {
    return try {
        val bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
        bitmap.asImageBitmap()
    } catch (e: IOException) {
        e.printStackTrace()
        null
        }
}