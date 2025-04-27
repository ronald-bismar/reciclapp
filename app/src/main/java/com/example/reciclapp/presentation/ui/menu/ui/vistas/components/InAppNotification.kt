package com.example.reciclapp.presentation.ui.menu.ui.vistas.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.reciclapp.data.services.notification.NotificationEventBus
import com.example.reciclapp.domain.entities.Mensaje
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InAppNotification(onNotificationClick: (Mensaje) -> Unit = {}, onNotificationAccepted : (Mensaje) -> Unit = {}, onSendNewMessage: (Mensaje) -> Unit = {}) {
    val scope = rememberCoroutineScope()
    var currentNotification by remember { mutableStateOf<Mensaje?>(null) }
    var isVisible by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        NotificationEventBus.notificationEvents.collect { mensaje ->
            currentNotification = mensaje

            // Verificar si es una oferta aceptada
            if (mensaje.titleMessage == "Oferta aceptada") {
                showBottomSheet = true
            } else {
                isVisible = true
                scope.launch {
                    delay(5000) // Auto-dismiss after 5 seconds
                    isVisible = false
                }
            }
        }
    }

    // Bottom Sheet para "Oferta aceptada"
    currentNotification?.let { mensaje ->
        if (showBottomSheet && mensaje.titleMessage == "Oferta aceptada") {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = bottomSheetState,
                dragHandle = { BottomSheetDefaults.DragHandle() }
            ) {
                OfertaAceptadaBottomSheet(
                    mensaje = mensaje,
                    onVerUbicacionClick = {
                        onNotificationAccepted(mensaje)
                        scope.launch {
                            bottomSheetState.hide()
                            showBottomSheet = false
                        }
                    },
                    onSendNewMessage = {
                        onSendNewMessage(mensaje)
                        scope.launch {
                            bottomSheetState.hide()
                            showBottomSheet = false
                        }
                    },
                    onDismiss = {
                        scope.launch {
                            bottomSheetState.hide()
                            showBottomSheet = false
                        }
                    }
                )
            }
        }
    }

    // Notificación estándar para otros mensajes
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(Float.MAX_VALUE) // Asegura que esté por encima de cualquier otro componente
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(initialOffsetY = { -it }),
            exit = slideOutVertically(targetOffsetY = { -it })
        ) {
            currentNotification?.let { mensaje ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shadowElevation = 4.dp,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable {
                                onNotificationClick(mensaje)
                                isVisible = false
                            }
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = mensaje.titleMessage.ifEmpty { "Nuevo mensaje" },
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = mensaje.contenido,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            IconButton(onClick = { isVisible = false }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Cerrar"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OfertaAceptadaBottomSheet(
    mensaje: Mensaje,
    onVerUbicacionClick: () -> Unit,
    onSendNewMessage: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Círculo con ícono de éxito
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = "Oferta Aceptada",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Título
        Text(
            text = "¡Oferta Aceptada!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Descripción
        Text(
            text = "El comprador ha aceptado tu oferta por los productos.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mensaje del comprador
        if (mensaje.contenido.isNotEmpty()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Mensaje del comprador:",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = mensaje.contenido,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para ver ubicación
        Button(
            onClick = onVerUbicacionClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Ver ubicación del comprador")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Botón para ver ubicación
        Button(
            onClick = onSendNewMessage,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                imageVector = Icons.Default.Message,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Enviar nuevo mensaje")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para cerrar
        Text(
            text = "Cerrar",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable { onDismiss() }
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}