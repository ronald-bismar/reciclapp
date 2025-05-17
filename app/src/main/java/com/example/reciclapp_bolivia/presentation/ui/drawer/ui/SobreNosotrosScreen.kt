package com.example.reciclapp_bolivia.presentation.ui.aboutus

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reciclapp_bolivia.R

@Composable
fun AboutUsScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Título principal
            Text(
                text = "Sobre Nosotros",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Imagen del logo o destacada
            Image(
                painter = painterResource(id = R.drawable.reciclapgrandesinfondo), // Cambia al nombre de tu imagen
                contentDescription = "Logo de ReciclApp",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 16.dp)
            )

            // Subtítulo - Quiénes somos
            Text(
                text = "¿Quiénes somos?",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Texto sobre quiénes somos
            Text(
                text = """
                        ReciclApp es una iniciativa dedicada a la promoción del reciclaje y la sostenibilidad ambiental. Nuestro equipo está compuesto por entusiastas comprometidos con la educación ambiental y la implementación de soluciones tecnológicas para un futuro más limpio.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Imagen relacionada con el equipo
            Image(
                painter = painterResource(id = R.drawable.img_reciclando2), // Cambia al nombre de tu imagen
                contentDescription = "Equipo de ReciclApp",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 16.dp)
            )

            // Subtítulo - Nuestra historia
            Text(
                text = "Nuestra Historia",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Texto sobre la historia
            Text(
                text = """
                        Nacimos con el propósito de facilitar el reciclaje y fomentar prácticas sostenibles en comunidades de todo el mundo. Desde nuestros inicios, hemos trabajado para conectar a las personas con recursos y herramientas que hagan del reciclaje un hábito accesible y práctico.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Imagen representativa de la historia
            Image(
                painter = painterResource(id = R.drawable.tachoss), // Cambia al nombre de tu imagen
                contentDescription = "Nuestra historia",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 16.dp)
            )

            // Subtítulo - Nuestros valores
            Text(
                text = "Nuestros Valores",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Texto sobre los valores
            Text(
                text = """
                        • Compromiso con el medio ambiente.
                        • Innovación para la sostenibilidad.
                        • Educación y concienciación.
                        • Trabajo en equipo y colaboración.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAboutUsScreen() {
    AboutUsScreen()
}
