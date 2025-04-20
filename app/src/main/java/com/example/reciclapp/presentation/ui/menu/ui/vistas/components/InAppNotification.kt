package com.example.reciclapp.presentation.ui.common

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.reciclapp.data.services.notification.NotificationEventBus
import com.example.reciclapp.domain.entities.Mensaje
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InAppNotification(onNotificationClick: (Mensaje) -> Unit = {}) {
    val scope = rememberCoroutineScope()
    var currentNotification by remember { mutableStateOf<Mensaje?>(null) }
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        NotificationEventBus.notificationEvents.collect { mensaje ->
            currentNotification = mensaje
            isVisible = true
            scope.launch {
                delay(5000) // Auto-dismiss after 5 seconds
                isVisible = false
            }
        }
    }

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
                                    text = "Nuevo mensaje",
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