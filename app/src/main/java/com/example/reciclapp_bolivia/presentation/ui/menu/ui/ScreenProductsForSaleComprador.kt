package com.example.reciclapp_bolivia.presentation.ui.menu.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.reciclapp_bolivia.R
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.presentation.ui.menu.ui.content.myproducts.ProductoEtiqueta
import com.example.reciclapp_bolivia.presentation.viewmodel.MensajeViewModel
import com.example.reciclapp_bolivia.presentation.viewmodel.TransaccionViewModel
import com.example.reciclapp_bolivia.presentation.viewmodel.UserViewModel
import com.example.reciclapp_bolivia.util.NameRoutes.QRGENERATORSCREEN

@Composable
fun ScreenProductsForSale(
    userViewModel: UserViewModel,
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
                userViewModel = userViewModel,
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
                    text = "Contactar a comprador por ${selectedProducts.size} producto${if (selectedProducts.size > 1) "s" else ""}",
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
    userViewModel: UserViewModel,
    selectedProducts: MutableList<ProductoReciclable>
) {
    val myProducts = userViewModel.productosAsVendedor.collectAsState()

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(myProducts.value.size) { index ->
            val producto = myProducts.value[index]
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
                // Verificar si este producto está en la lista de seleccionados
                isSelected = selectedProducts.contains(producto)
            )
        }
        // Añadir espacio al final para que el botón flotante no tape el último item
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun TituloProductsForSale() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Que deseas vender",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center
    )
}

@Composable
fun CardProductoToSell(
    producto: ProductoReciclable,
    isSelected: Boolean = false,
    onSelectionChanged: (Boolean) -> Unit
) {
    // Mantener el estado localmente pero sincronizado con el estado externo
    var isSelectedLocal by remember { mutableStateOf(isSelected) }

    // Sincronizar con el estado externo
    if (isSelected != isSelectedLocal) {
        isSelectedLocal = isSelected
    }

// Define the background color with improved animation
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelectedLocal)
            Color(red = 0.8f, green = 0.93f, blue = 0.8f)
        else
            MaterialTheme.colorScheme.surface,
        animationSpec = tween(
            durationMillis = 350,
            easing = FastOutSlowInEasing
        ),
        label = "backgroundColor"
    )

    // Animate elevation for pressed effect
    val elevation by animateDpAsState(
        targetValue = if (isSelectedLocal) 4.dp else 2.dp,
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        ),
        label = "elevation"
    )

    // Animate border color
    val borderColor by animateColorAsState(
        targetValue = if (isSelectedLocal)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
        animationSpec = tween(
            durationMillis = 350,
            easing = FastOutSlowInEasing
        ),
        label = "borderColor"
    )

    // Add ripple effect with custom color
    val interactionSource = remember { MutableInteractionSource() }
    val rippleColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(color = rippleColor),
                onClick = {
                    isSelectedLocal = !isSelectedLocal
                    onSelectionChanged(isSelectedLocal)
                }
            ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = elevation
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Animated checkbox
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(18.dp)
            ) {
                Checkbox(
                    checked = isSelectedLocal,
                    onCheckedChange = {
                        isSelectedLocal = it
                        onSelectionChanged(it)
                    },
                    modifier = Modifier
                        .size(24.dp)
                        .scale(
                            animateFloatAsState(
                                targetValue = if (isSelectedLocal) 1.1f else 1f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                ),
                                label = "checkboxScale"
                            ).value
                        ),
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
                    )
                )
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Image with subtle scale animation
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(producto.urlImagenProducto)
                            .crossfade(true)
                            .build(),
                        contentDescription = producto.nombreProducto,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .scale(
                                animateFloatAsState(
                                    targetValue = if (isSelectedLocal) 1.05f else 1f,
                                    animationSpec = tween(300),
                                    label = "imageScale"
                                ).value
                            ),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.icono_defecto),
                        error = painterResource(R.drawable.icono_defecto)
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = producto.nombreProducto,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = animateColorAsState(
                                targetValue = if (isSelectedLocal)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurface,
                                label = "titleColor"
                            ).value
                        )
                        Text(
                            text = producto.detallesProducto,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

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
}