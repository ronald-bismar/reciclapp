package com.nextmacrosystem.reciclapp.presentation.ui.drawer.ui

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
import com.nextmacrosystem.reciclapp.R

@Composable
fun SimpleAyudaScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Permite desplazamiento vertical
        ) {
            // Título principal
            Text(
                text = "Ayuda",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Imagen entre "Ayuda" y "¿Qué es ReciclApp?"
            Image(
                painter = painterResource(id = R.drawable.reciclapgrandesinfondo), // Reemplaza con el nombre de tu imagen
                contentDescription = "Icono de Reciclaje",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 16.dp)
            )

            // Subtítulo
            Text(
                text = "¿Qué es ReciclApp?",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Texto explicativo
            Text(
                text = """
                        ReciclApp es una aplicación móvil diseñada para ayudarte a identificar rápidamente el contenedor adecuado para reciclar diferentes tipos de residuos, como vidrio, plástico, orgánicos, entre otros. A través de un buscador intuitivo, simplemente introduces el nombre del residuo que deseas desechar, y el sistema te indicará el contenedor correcto donde debe ser reciclado.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                )
            )
            Image(
                painter = painterResource(id = R.drawable.tachoss), // Reemplaza con el nombre de tu imagen
                contentDescription = "imagen de tachoss",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 16.dp)
            )
            Text(
                text = """                        
                        Además, ReciclApp aprovecha la geolocalización de tu smartphone para mostrarte los puntos de reciclaje más cercanos a tu ubicación, brindándote detalles sobre su dirección y horarios de atención.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                )
            )

            Image(
                painter = painterResource(id = R.drawable.tienda), // Reemplaza con el nombre de tu imagen
                contentDescription = "imagen de tachoss",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 16.dp)
            )
            Text(
                text = """                        
                        Conscientes de la importancia de mantener nuestro entorno limpio y de educar a las futuras generaciones sobre la relevancia del reciclaje, ReciclApp también te proporciona información valiosa sobre los beneficios ambientales de reciclar. La aplicación incluso te permite calificar los puntos de reciclaje que visitas, fomentando la participación activa y el compromiso con el medio ambiente.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSimpleAyudaScreen() {
    SimpleAyudaScreen()
}
