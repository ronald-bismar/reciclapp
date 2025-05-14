package com.example.reciclapp_bolivia.presentation.ui.drawer.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reciclapp.R

@Composable
fun CalificanosScreen() {
    val context = LocalContext.current
    var rating by remember { mutableStateOf(0f) }
    var showThankYouDialog by remember { mutableStateOf(false) }

    // Mapa de puntuaciones y categorías
    val rankingData = listOf(
        5 to "Me super encanta",
        4 to "Me encanta",
        3 to "Me super gusta",
        2 to "Me gusta",
        1 to "Neutral",
        0 to "No me gusta"
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Título principal
            Text(
                text = "¡Califica nuestra aplicación!",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Imagen destacada
            Image(
                painter = painterResource(id = R.drawable.reciclapgrandesinfondo),
                contentDescription = "Logo de ReciclApp",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 16.dp)
            )

            // Descripción
            Text(
                text = "¿Te está gustando nuestra aplicación? Califica y comparte tus comentarios con nosotros. " +
                        "Tu opinión es muy importante para seguir mejorando.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Puntuación con estrellas
            RatingBar(rating = rating, onRatingChanged = { rating = it })

            // Label showing rating value
            Text(
                text = "Puntuación: ${rating.toInt()}/5",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )



            Spacer(modifier = Modifier.height(16.dp))

            // Botón para enviar calificación
            Button(
                onClick = {
                    showThankYouDialog = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8))
            ) {
                Text(
                    text = "Enviar Calificación",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón para ir a la tienda de aplicaciones para calificar
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.example.reciclapp"))
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34A853))
            ) {
                Text(
                    text = "Califica en Google Play",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        // Agradecimiento cuando se envía la calificación
        if (showThankYouDialog) {
            ThankYouDialog(
                onDismiss = { showThankYouDialog = false }
            )
        }
    }
}


@Composable
fun RatingBar(rating: Float, onRatingChanged: (Float) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        for (i in 1..5) {
            val icon = if (i <= rating) {
                Icons.Filled.Star
            } else {
                Icons.Outlined.FavoriteBorder
            }
            IconButton(
                onClick = { onRatingChanged(i.toFloat()) }
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Rating Star",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun ThankYouDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("¡Gracias por calificarnos!") },
        text = { Text("Tu participación nos ayuda mucho a mejorar nuestra aplicación. ¡Gracias por tu apoyo!") },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Aceptar")
            }
        },
        modifier = Modifier.padding(24.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCalificarScreen() {
    CalificanosScreen()
}
