package com.example.reciclapp_bolivia.presentation.ui.menu.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.reciclapp.R
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.presentation.viewmodel.MensajeViewModel
import com.example.reciclapp_bolivia.util.NameRoutes.PANTALLAPRINCIPAL
import kotlin.math.min

private const val TAG = "CompradorOfertaScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompradorOfertaScreen(
    idMensaje: String,
    mensajeViewModel: MensajeViewModel,
    navHostController: NavHostController
) {
    var contraofertaMode by remember { mutableStateOf(false) }
    var contrapreciosMap by remember { mutableStateOf(mapOf<String, Double>()) }
    var showSuccessMessage by remember { mutableStateOf(false) }

    val isLoading by mensajeViewModel.isLoading.collectAsState()
    val productos by mensajeViewModel.productos.collectAsState()
    val usuarioQueSeContacto by mensajeViewModel.usuarioContactado.collectAsState()
    val message by mensajeViewModel.message.collectAsState()

    var showAceptacionModal by remember { mutableStateOf(false) }
    var showContraofertaModal by remember { mutableStateOf(false) }


    // Estado de los bottom sheets
    val aceptacionSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val contraofertaSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    var mensaje by remember { mutableStateOf("") }

    LaunchedEffect(idMensaje) {
        mensajeViewModel.getMessage(idMensaje)
    }

    val inicializarContraprecios = {
        contrapreciosMap = productos.associate { it.idProducto to it.precio }
    }

    // Llamar a inicializar al principio si el mapa está vacío
    if (contrapreciosMap.isEmpty() && productos.isNotEmpty()) {
        inicializarContraprecios()
    }

    if (showAceptacionModal) {
        ModalBottomSheet(
            onDismissRequest = { showAceptacionModal = false },
            sheetState = aceptacionSheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            AceptacionOfertaModalContent(
                onNavigateClick = {
                    showAceptacionModal = false
                    navHostController.navigate(PANTALLAPRINCIPAL)
                }
            )
        }
    }

    // Modal de Contraoferta Enviada
    if (showContraofertaModal) {
        ModalBottomSheet(
            onDismissRequest = { showContraofertaModal = false },
            sheetState = contraofertaSheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            ContraofertaEnviadaModalContent(
                onNavigateClick = {
                    showContraofertaModal = false
                    navHostController.navigate(PANTALLAPRINCIPAL)
                }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (contraofertaMode) "Realizar contraoferta" else "Oferta recibida",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver atrás"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            if (contraofertaMode && !showSuccessMessage) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // TextField
                    OutlinedTextField(
                        value = mensaje,
                        onValueChange = { mensaje = it },
                        placeholder = { Text("Escribe tu mensaje...") },
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 56.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Send
                        ),
                        shape = RoundedCornerShape(12.dp)
                        // Mantenemos los colores por defecto para tener los bordes negros con fondo blanco
                    )
                    // FloatingActionButton
                    FloatingActionButton(
                        onClick = {
                            val newMessage = message.copy(
                                contenido = mensaje
                            )
                            mensajeViewModel.enviarContraofertaAVendedor(
                                contrapreciosMap,
                                newMessage,
                                usuarioQueSeContacto?.tokenNotifications ?: "",
                            )
                            showSuccessMessage = true
                            contraofertaMode = false
                            showContraofertaModal = true
                        },
                        containerColor = MaterialTheme.colorScheme.secondary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Enviar contraoferta",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Text(
                            text = "Cargando datos...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            } else if (productos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay productos disponibles",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                // Contenido principal
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Encabezado del vendedor
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            VendedorHeader(usuarioQueSeContacto ?: Usuario())

                            MessageVendedor(message.contenido)

                            AnimatedVisibility(
                                visible = contraofertaMode,
                                enter = fadeIn() + scaleIn(),
                                exit = fadeOut() + scaleOut()
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(top = 12.dp)
                                ) {
                                    Text(
                                        text = "Edita los precios para hacer tu contraoferta",
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Título de la sección
                    Text(
                        text = "Productos ofrecidos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Lista de productos
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(productos.count()) { index ->
                            if (contraofertaMode) {
                                ProductoContraofertaItem(
                                    producto = productos[index],
                                    contraprecio = contrapreciosMap[productos[index].idProducto]
                                        ?: productos[index].precio,
                                    onPrecioChange = { nuevoPrecio ->
                                        contrapreciosMap =
                                            contrapreciosMap + (productos[index].idProducto to nuevoPrecio)
                                    }
                                )
                            } else {
                                ProductoOfertaItem(
                                    producto = productos[index]
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botones de acción (solo visibles cuando no estamos en modo contraoferta)
                    AnimatedVisibility(
                        visible = !contraofertaMode && !showSuccessMessage,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            OutlinedTextField(
                                value = mensaje,
                                onValueChange = { mensaje = it },
                                placeholder = { Text("Escribe tu mensaje...") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 64.dp),
                                singleLine = false,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done
                                ),
                                shape = RoundedCornerShape(12.dp),
                            )
                            Button(
                                onClick = {
                                    showSuccessMessage = true
                                    val newMessage = message.copy(
                                        contenido = mensaje
                                    )

                                    Log.d(TAG, "usuarioQueSeContacto: $usuarioQueSeContacto")

                                    mensajeViewModel.compradorAceptaOferta(
                                        newMessage, usuarioQueSeContacto?.tokenNotifications ?: ""
                                    )
                                    showAceptacionModal = true
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Icon(
                                    Icons.Rounded.Check,
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 8.dp),
                                    tint = Color.Black
                                )
                                Text(
                                    text = "Aceptar",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    color = Color.Black
                                )
                            }

                            TextButton(
                                onClick = {
                                    inicializarContraprecios()
                                    contraofertaMode = true
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Realizar contraoferta",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.secondary,
                                )
                            }
                        }
                    }

                    // Mensaje de éxito
                    AnimatedVisibility(
                        visible = showSuccessMessage,
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {
                        Button(
                            onClick = {
                                navHostController.navigate(PANTALLAPRINCIPAL)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary, // Usa primary como pediste
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(4.dp) // Menos redondeado
                        ) {
                            Text(
                                text = "Volver a la pantalla principal",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = Color.Black
                            )
                        }                    }
                }
            }
        }
    }
}

@Composable
fun MessageVendedor(messageContent: String) {
    Text(
        text = messageContent,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun VendedorHeader(usuarioQueEnvioOferta: Usuario) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Avatar del vendedor con borde y sombra
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(usuarioQueEnvioOferta.urlImagenPerfil)
                    .crossfade(true)
                    .build(),
                contentDescription = "Avatar de ${usuarioQueEnvioOferta.nombre}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape),
                placeholder = painterResource(R.drawable.icono_defecto),
                error = painterResource(R.drawable.icono_defecto)
            )
        }

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${usuarioQueEnvioOferta.nombre} ${usuarioQueEnvioOferta.apellido}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "desea vender estos productos",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ProductoOfertaItem(producto: ProductoReciclable) {
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
                    }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEDF7ED) // Fondo verde claro
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Icono del producto con borde y fondo
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color.White,
                modifier = Modifier.size(56.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(producto.urlImagenProducto)
                        .crossfade(true)
                        .build(),
                    contentDescription = producto.nombreProducto,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    placeholder = painterResource(R.drawable.icono_defecto),
                    error = painterResource(R.drawable.icono_defecto)
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto.nombreProducto,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "${producto.puntosPorCompra} puntos",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    // Mostrar unidad de medida
                    if (producto.cantidad.toString()
                            .isNotBlank() && producto.unidadMedida.isNotBlank()
                    ) {
                        Text(
                            text = "• ${producto.cantidad} ${producto.unidadMedida}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }

            // Precio con fondo para destacarlo
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = "${producto.precio} ${producto.monedaDeCompra}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoContraofertaItem(
    producto: ProductoReciclable,
    contraprecio: Double,
    onPrecioChange: (Double) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF0F8FF) // Fondo azul muy claro
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Icono del producto con borde y fondo
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White,
                    modifier = Modifier.size(56.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(producto.urlImagenProducto)
                            .crossfade(true)
                            .build(),
                        contentDescription = producto.nombreProducto,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        placeholder = painterResource(R.drawable.icono_defecto),
                        error = painterResource(R.drawable.icono_defecto)
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = producto.nombreProducto,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "${producto.puntosPorCompra} puntos",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )

                        // Mostrar unidad de medida
                        if (producto.unidadMedida.isNotBlank()) {
                            Text(
                                text = "• ${producto.unidadMedida}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }

                // Información del precio original
                Text(
                    text = "${producto.precio} ${producto.monedaDeCompra}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(end = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Etiqueta de tu oferta
                Text(
                    text = "Tu oferta:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Campo de entrada para contraoferta con mejor estilo
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Editar precio",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )

                            OutlinedTextField(
                                value = contraprecio.toString(),
                                onValueChange = {
                                    val newValue = it.toDoubleOrNull() ?: contraprecio
                                    onPrecioChange(newValue)
                                },
                                modifier = Modifier
                                    .size(width = 80.dp, height = 56.dp)
                                    .padding(start = 8.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                textStyle = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    containerColor = Color.Transparent
                                )
                            )

                            Text(
                                text = producto.monedaDeCompra,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
// Modal para Aceptación de Oferta
@Composable
fun AceptacionOfertaModalContent(onNavigateClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Animación de check elegante
        val rotationState by animateFloatAsState(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 800,
                easing = LinearOutSlowInEasing
            ),
            label = "checkRotation"
        )

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Oferta aceptada",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(80.dp)
                    .graphicsLayer {
                        rotationZ = 360f * rotationState
                        scaleX = min(1f, rotationState * 1.2f)
                        scaleY = min(1f, rotationState * 1.2f)
                    }
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "¡Oferta Aceptada!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Tu aceptación de la oferta ha sido registrada con éxito. Pronto te contactaremos para coordinar los detalles.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onNavigateClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Volver a la pantalla principal",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Modal para Contraoferta Enviada
@Composable
fun ContraofertaEnviadaModalContent(onNavigateClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Animación para el icono de contraoferta
        val rotationState by animateFloatAsState(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearOutSlowInEasing
            ),
            label = "swapIconRotation"
        )

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.SwapHoriz,
                contentDescription = "Contraoferta enviada",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .size(80.dp)
                    .graphicsLayer {
                        rotationY = 360f * rotationState
                        scaleX = min(1f, rotationState * 1.2f)
                        scaleY = min(1f, rotationState * 1.2f)
                    }
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "¡Contraoferta Enviada!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Tu contraoferta ha sido enviada al vendedor. Te notificaremos cuando recibamos una respuesta.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onNavigateClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Volver a la pantalla principal",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}