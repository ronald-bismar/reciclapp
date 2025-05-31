package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.nextmacrosystem.reciclapp.domain.entities.Usuario

@Composable
fun ContactCard(
    usuario: Usuario,
    viewProfile: () -> Unit,
    sendMessage: () -> Unit,
    call: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(10.dp, shape = MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.onSurface),
        shape = MaterialTheme.shapes.medium,
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Profile Image
                Image(
                    painter = rememberAsyncImagePainter(model = usuario.urlImagenPerfil),
                    contentDescription = "Perfil",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .clickable { /* Abrir una vista ampliada o un perfil */ },
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                // User Info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "${usuario.nombre} ${usuario.apellido}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Points Display with Star Icon
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Puntos",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${usuario.puntaje} puntos",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Address Display with Location Icon
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Ubicación",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = usuario.direccion,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }            }

            Spacer(modifier = Modifier.height(6.dp))

            // Action Buttons
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, shape = MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surface),
            ) {
                ActionButton(
                    icon = Icons.Default.Person,
                    label = "Ver",
                    color = MaterialTheme.colorScheme.primary,
                    onClick = viewProfile,
                )
                ActionButton(
                    icon = Icons.Default.Email,
                    label = "Mensaje",
                    color = MaterialTheme.colorScheme.secondary,
                    onClick = sendMessage
                )
                ActionButton(
                    icon = Icons.Default.Call,
                    label = "Llamar",
                    color = MaterialTheme.colorScheme.tertiary,
                    onClick = call,
                )
            }
        }
    }
}

@Composable
fun ActionButton(icon: ImageVector, label: String, color: Color, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = color
        )
    }
}


@Preview(showBackground = true)
@Composable
fun contactCardPreview(){
    val usuario = Usuario(
        idUsuario = "user123",
        nombre = "Ronald",
        apellido = "Bismar",
        telefono = 591712345678,
        correo = "ronald.bismar@example.com",
        contrasena = "********",
        direccion = "Av. Busch #1234, La Paz, Bolivia",
        urlImagenPerfil = "https://i.pravatar.cc/300",
        tipoDeUsuario = "Comprador",
        puntaje = 850,
        nombreNivel = "Reciclador Experto",
        nivel = "3",
        logrosPorId = "1,2,3"
    )
    ContactCard(usuario, {}, {}, {})
}