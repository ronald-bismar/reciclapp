package com.example.reciclapp.presentation.ui.menu.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.presentation.viewmodel.MensajeViewModel
import com.example.reciclapp.presentation.viewmodel.TransaccionViewModel
import com.example.reciclapp.presentation.viewmodel.VendedoresViewModel
import com.example.reciclapp.util.NameRoutes.QRGENERATORSCREEN

@Composable
fun ScreenProductsForSaleVendedor(
    vendedoresViewModel: VendedoresViewModel,
    transaccionViewModel: TransaccionViewModel,
    mensajeViewModel: MensajeViewModel,
    mainNavController: NavHostController
) {
    val userContacted = transaccionViewModel.usuarioContactado.collectAsState().value
    val selectedProducts = remember { mutableStateListOf<ProductoReciclable>() }

    val hasSelectedProducts by remember {
        derivedStateOf { selectedProducts.isNotEmpty() }
    }

    val density = LocalDensity.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            TituloProductsForSale()
            ListaProductos(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                vendedoresViewModel = vendedoresViewModel,
                selectedProducts = selectedProducts
            )
        }

        // Botón contactar que aparece cuando hay productos seleccionados
        AnimatedVisibility(
            visible = hasSelectedProducts,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            enter = slideInVertically { with(density) { 100.dp.roundToPx() } } +
                    expandVertically(expandFrom = Alignment.Bottom) +
                    fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically { with(density) { 100.dp.roundToPx() } } +
                    shrinkVertically(shrinkTowards = Alignment.Bottom) +
                    fadeOut()
        ) {
            Button(
                onClick = {
                    userContacted?.let {
                        transaccionViewModel.prepareTransaction(it, selectedProducts)
                        mensajeViewModel.setProductosSeleccionados(selectedProducts)
                    }

                    mainNavController.navigate(QRGENERATORSCREEN)
                },
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(28.dp),
                        spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    )
                    .height(56.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Contactar a vendedor por ${selectedProducts.size} producto${if (selectedProducts.size > 1) "s" else ""}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun ListaProductos(
    modifier: Modifier,
    vendedoresViewModel: VendedoresViewModel,
    selectedProducts: MutableList<ProductoReciclable>
) {
    val productsVendedor = vendedoresViewModel.productos.collectAsState().value.filter { producto ->
        producto.fueVendida == false
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(productsVendedor.size) { index ->
            val producto = productsVendedor[index]
            CardProductoToSell(
                producto = producto,
                onSelectionChanged = { isSelected ->
                    if (isSelected) {
                        if (!selectedProducts.contains(producto)) {
                            selectedProducts.add(producto)
                        }
                    } else {
                        selectedProducts.remove(producto)
                    }
                },
                isSelected = selectedProducts.contains(producto)
            )
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}


@Composable
private fun TituloProductsForSale() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Que productos deseas comprar",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center
    )
}