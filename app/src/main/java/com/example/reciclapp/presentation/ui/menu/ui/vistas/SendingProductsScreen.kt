package com.example.reciclapp.presentation.ui.menu.ui.vistas

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.reciclapp.R
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.presentation.states.SendingProductsState
import com.example.reciclapp.presentation.ui.registro.ui.showToast
import com.example.reciclapp.presentation.viewmodel.MensajeViewModel
import com.example.reciclapp.presentation.viewmodel.TransaccionViewModel
import com.example.reciclapp.util.NameRoutes.PANTALLAPRINCIPAL
import com.example.reciclapp.util.TipoDeUsuario

@Composable
fun SendingProductsScreen(
    navHostController: NavController,
    transaccionViewModel: TransaccionViewModel,
    mensajeViewModel: MensajeViewModel
) {

    val myUser = transaccionViewModel.myUser.value
    val usuarioContactado = transaccionViewModel.usuarioContactado.collectAsState().value
    val productosSeleccionados = transaccionViewModel.productosSeleccionados.collectAsState().value
    val state = mensajeViewModel.sendingProductsState.collectAsState()

    var mensaje by remember { mutableStateOf("") }
    var showCompletedModal by remember { mutableStateOf(false) }
    var messageButtonSend by remember { mutableStateOf("Enviar Mensaje") }

    val context = LocalContext.current

    val animatedContentAlpha by animateFloatAsState(
        targetValue = if (state.value == SendingProductsState.Loading) 0.6f else 1f,
        animationSpec = tween(durationMillis = 500),
        label = "contentAlpha"
    )

    LaunchedEffect(state.value) {
        when (val currentState = state.value) {
            is SendingProductsState.Error -> {
                Toast.makeText(
                    context,
                    currentState.error,
                    Toast.LENGTH_SHORT
                ).show()
            }

            SendingProductsState.Success -> showCompletedModal = true
            SendingProductsState.Loading -> messageButtonSend = "Enviando Mensaje..."
            else -> {}
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            mensajeViewModel.resetSendingProductsState()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .alpha(animatedContentAlpha),
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
                    .alpha(animatedContentAlpha),
                verticalArrangement = Arrangement.Center,
            ) {
                // Header con título
                Text(
                    text = "Enviando productos a ${usuarioContactado?.tipoDeUsuario ?: "al usuario contactado"}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 24.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(1f))


                PerfilesConexion(
                    usuarioActual = myUser ?: Usuario(),
                    vendedor = usuarioContactado ?: Usuario(),
                    isLoading = state.value == SendingProductsState.Loading
                )

                Spacer(modifier = Modifier.weight(1f))

                CampoDeMensaje(mensaje, onMensajeChange = { mensaje = it })

                Spacer(modifier = Modifier.weight(1f))

                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(500)) +
                            expandVertically(animationSpec = tween(500)),
                    exit = fadeOut(animationSpec = tween(300))
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        items(productosSeleccionados.size) { index ->
                            ProductoEnvioItem(
                                producto = productosSeleccionados[index],
                                onClick = { }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        when (usuarioContactado?.tipoDeUsuario?.uppercase()) {
                            TipoDeUsuario.COMPRADOR ->
                                mensajeViewModel.enviarOfertaAComprador(mensaje)

                            TipoDeUsuario.VENDEDOR ->
                                mensajeViewModel.enviarOfertaAVendedor(mensaje)

                            else -> showToast(context, "No se encontro al usuario contactado")
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = messageButtonSend,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
        if (showCompletedModal) {
            CompletedTransactionModal(
                onNavigate = {
                    showCompletedModal = false
                    navHostController.navigate(PANTALLAPRINCIPAL) {
                        popUpTo(PANTALLAPRINCIPAL) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
fun CampoDeMensaje(mensaje: String, onMensajeChange: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Mensaje",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )
        OutlinedTextField(
            value = mensaje,
            onValueChange = onMensajeChange,
            placeholder = { Text("Mensaje opcional...") },
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


}

@Composable
fun PerfilesConexion(
    usuarioActual: Usuario,
    vendedor: Usuario,
    isLoading: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Perfil del usuario
        PerfilUsuario(
            usuario = usuarioActual,
            isLoading = isLoading,
            modifier = Modifier.size(70.dp)
        )

        // Línea de conexión
        Canvas(
            modifier = Modifier
                .weight(1f)
                .height(3.dp)
        ) {
            val path = Path().apply {
                moveTo(0f, size.height / 2)
                cubicTo(
                    size.width * 0.2f, size.height * 0.1f,
                    size.width * 0.8f, size.height * 0.1f,
                    size.width, size.height / 2
                )
            }
            drawPath(
                path = path,
                color = Color(0xFF4CAF50),
                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        // Perfil del club de reciclaje
        PerfilUsuario(
            usuario = vendedor,
            isLoading = isLoading,
            modifier = Modifier.size(70.dp)
        )
    }
}

@Composable
fun PerfilUsuario(
    usuario: Usuario,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        if (isLoading) {
            ShimmerEffect(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(70.dp)
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(usuario.urlImagenPerfil)
                    .crossfade(true)
                    .build(),
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .size(70.dp),
                placeholder = painterResource(R.drawable.perfil),
                error = painterResource(R.drawable.perfil)
            )
        }
    }
}

@Composable
fun ProductoEnvioItem(
    producto: ProductoReciclable,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "cardScale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = { onClick() }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Imagen del producto
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(producto.urlImagenProducto)
                    .crossfade(true)
                    .build(),
                contentDescription = producto.nombreProducto,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                placeholder = painterResource(R.drawable.icono_defecto),
                error = painterResource(R.drawable.icono_defecto)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto.nombreProducto,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${producto.puntosPorCompra} puntos",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Text(
                        text = "${producto.precio} Bs",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}

@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier
) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(10f, 10f),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )

    Box(
        modifier = modifier
            .background(brush)
    )
}

@Composable
fun CompletedTransactionModal(
    onNavigate: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .pointerInput(Unit) {
                    detectTapGestures { }  // Evitar que los clics en la tarjeta cierren el modal
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Círculo de éxito con animación
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            color = Color(0xFF4CAF50).copy(alpha = 0.1f),
                            shape = CircleShape
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Puedes usar un ícono de checkmark o dibujar uno con Canvas
                    Canvas(modifier = Modifier.size(40.dp)) {
                        drawLine(
                            color = Color(0xFF4CAF50),
                            start = Offset(size.width * 0.2f, size.height * 0.5f),
                            end = Offset(size.width * 0.45f, size.height * 0.7f),
                            strokeWidth = 8f,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = Color(0xFF4CAF50),
                            start = Offset(size.width * 0.45f, size.height * 0.7f),
                            end = Offset(size.width * 0.8f, size.height * 0.3f),
                            strokeWidth = 8f,
                            cap = StrokeCap.Round
                        )
                    }
                }

                // Título y mensaje
                Text(
                    text = "¡Transacción Completada!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Tu oferta ha sido enviada exitosamente al comprador",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Botón para continuar
                Button(
                    onClick = onNavigate,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Continuar",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}