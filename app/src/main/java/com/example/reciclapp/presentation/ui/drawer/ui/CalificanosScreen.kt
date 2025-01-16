package com.example.reciclapp.presentation.ui.drawer.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
            RatingBar(rating = 4.5f)

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para enviar calificación
            Button(
                onClick = {
                    // Aquí puedes agregar funcionalidad para enviar la calificación
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

            // Ranking de puntuaciones
            Text(
                text = "Ranking de Puntuaciones",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Lista de usuarios con sus puntuaciones
            RankingItem(user = "Usuario 1", points = 120)
            RankingItem(user = "Usuario 2", points = 100)
            RankingItem(user = "Usuario 3", points = 85)

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
    }
}

@Composable
fun RatingBar(rating: Float) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        for (i in 1..5) {
            val icon = if (i <= rating) {
                Icons.Filled.Star
            } else {
                Icons.Filled.Star
            }
            Icon(
                imageVector = icon,
                contentDescription = "Rating Star",
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun RankingItem(user: String, points: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = user,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "$points puntos",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp
            ),
            color = Color(0xFF34A853)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPuntuacionesScreen() {
    CalificanosScreen()
}
