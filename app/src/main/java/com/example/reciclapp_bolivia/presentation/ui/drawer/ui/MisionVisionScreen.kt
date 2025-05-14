package com.example.reciclapp_bolivia.presentation.ui.drawer.ui

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
import com.example.reciclapp.R

@Composable
fun MisionVisionScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Título principal
            Text(
                text = "Misión y Visión",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Imagen entre el título principal y "Nuestra Misión"
            Image(
                painter = painterResource(id = R.drawable.reciclapgrandesinfondo), // Reemplaza con el nombre de tu imagen
                contentDescription = "Logo de ReciclApp",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 16.dp)
            )

            // Subtítulo - Misión
            Text(
                text = "Nuestra Misión",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Texto de la Misión
            Text(
                text = """
                        Promover el reciclaje y la sostenibilidad ambiental mediante una aplicación intuitiva que eduque, oriente y facilite el acceso a los recursos necesarios para desechar residuos correctamente. Nuestro objetivo es concienciar a las comunidades sobre la importancia de reducir el impacto ambiental y contribuir a un planeta más limpio.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Imagen relacionada con la Misión
            Image(
                painter = painterResource(id = R.drawable.img_reciclando2), // Reemplaza con el nombre de tu imagen
                contentDescription = "Imagen de Misión",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 16.dp)
            )

            // Subtítulo - Visión
            Text(
                text = "Nuestra Visión",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Texto de la Visión
            Text(
                text = """
                        Ser líderes en la promoción de prácticas sostenibles y en la generación de un impacto positivo en el medio ambiente, utilizando la tecnología como herramienta principal para conectar personas, recursos y conocimientos en torno al reciclaje y la conservación del planeta.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Imagen relacionada con la Visión
            Image(
                painter = painterResource(id = R.drawable.tachoss), // Reemplaza con el nombre de tu imagen
                contentDescription = "Imagen de Visión",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMisionVisionScreen() {
    MisionVisionScreen()
}
