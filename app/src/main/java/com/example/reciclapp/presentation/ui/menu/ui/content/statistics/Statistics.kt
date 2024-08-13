package com.example.reciclapp.presentation.ui.menu.ui.content.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reciclapp.domain.entities.Material
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

// Función para generar un color aleatorio
fun generateRandomColor(): Color {
    val random = Random.Default
    return Color(
        red = random.nextInt(256),
        green = random.nextInt(256),
        blue = random.nextInt(256)
    )
}

// Composable para mostrar un gráfico de torta moderno
@Composable
fun ModernChartPie(
    porcentajes: Array<Float>,
    labels: Array<String>,
    chartTitle: String,
    colors: List<Color> = List(porcentajes.size) { generateRandomColor() }
) {
    if (porcentajes.all { it == 0f }) {
        Text("No hay datos suficientes para mostrar el gráfico.", modifier = Modifier.padding(16.dp))
        return
    }

    val anguloInicial = -90f
    var anguloActual = anguloInicial
    val total = porcentajes.sum()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Título del gráfico
        BasicText(
            text = chartTitle,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dibuja el gráfico de torta
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Canvas(modifier = Modifier.size(300.dp).align(Alignment.Center)) {
                val centerX = size.width / 2
                val centerY = size.height / 2

                porcentajes.forEachIndexed { index, element ->
                    val anguloFinal = (element / total) * 360f
                    val midAngle = anguloActual + anguloFinal / 2

                    drawArc(
                        color = colors[index],
                        startAngle = anguloActual,
                        sweepAngle = anguloFinal,
                        useCenter = true,
                        style = Fill
                    )

                    anguloActual += anguloFinal

                    // Posiciona y dibuja el porcentaje sobre el gráfico
                    val textX = centerX + (size.width / 3) * cos(Math.toRadians(midAngle.toDouble()).toFloat())
                    val textY = centerY + (size.height / 3) * sin(Math.toRadians(midAngle.toDouble()).toFloat())

                    drawContext.canvas.nativeCanvas.drawText(
                        "${(element * 100).toInt()}%",
                        textX,
                        textY,
                        android.graphics.Paint().apply {
                            color = Color.Black.toArgb()
                            textSize = 30f
                            isFakeBoldText = true
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Composable para mostrar una lista de materiales con sus puntos y porcentajes, junto con los colores del gráfico
@Composable
fun MaterialPointsList(materials: List<Material>, colors: List<Color>, porcentajes: Array<Float>) {
    var totalPoints = 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Puntos por Material Reciclado",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Añadir los títulos de las columnas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Material",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Puntos/Unidad",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Total puntos",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Porcentaje",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            materials.forEachIndexed { index, material ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors[index])
                        .padding(vertical = 8.dp, horizontal = 4.dp)
                ) {
                    // Nombre del Material
                    Text(
                        text = material.nombre,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    // Puntos por Unidad
                    Text(
                        text = "${material.puntosPorUnidad} p/u",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    // Puntos Acumulados
                    Text(
                        text = "${material.puntosAcumulados} puntos",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    // Porcentaje
                    Text(
                        text = "${(porcentajes[index] * 100).toInt()}%",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                totalPoints += material.puntosAcumulados
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total de Puntos Acumulados: $totalPoints",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(76.dp))
    }
}

// Composable para mostrar la pantalla de estadísticas detalladas
@Composable
fun DetailedStatisticsScreen() {
    val materials = listOf(
        Material(
            idMaterial = 1,
            urlImagenMaterial = "url_plasticos.png",
            nombre = "Plásticos",
            descripcion = "Material reciclable de plásticos.",
            precio = 10.0,
            cantidad = 500,
            unidadDeMedida = "kg",
            puntosPorUnidad = 10,
            puntosAcumulados = 500,
            idComprador = 1
        ),
        Material(
            idMaterial = 2,
            urlImagenMaterial = "url_metales.png",
            nombre = "Metales",
            descripcion = "Material reciclable de metales.",
            precio = 15.0,
            cantidad = 750,
            unidadDeMedida = "kg",
            puntosPorUnidad = 15,
            puntosAcumulados = 750,
            idComprador = 2
        ),
        Material(
            idMaterial = 3,
            urlImagenMaterial = "url_papeleria.png",
            nombre = "Papelería",
            descripcion = "Material reciclable de papelería.",
            precio = 5.0,
            cantidad = 300,
            unidadDeMedida = "kg",
            puntosPorUnidad = 5,
            puntosAcumulados = 300,
            idComprador = 3
        ),
        Material(
            idMaterial = 4,
            urlImagenMaterial = "url_vidrieria.png",
            nombre = "Vidriería",
            descripcion = "Material reciclable de vidriería.",
            precio = 8.0,
            cantidad = 400,
            unidadDeMedida = "kg",
            puntosPorUnidad = 8,
            puntosAcumulados = 400,
            idComprador = 4
        ),
        Material(
            idMaterial = 5,
            urlImagenMaterial = "url_textiles.png",
            nombre = "Textiles",
            descripcion = "Material reciclable de textiles.",
            precio = 12.0,
            cantidad = 240,
            unidadDeMedida = "kg",
            puntosPorUnidad = 12,
            puntosAcumulados = 240,
            idComprador = 5
        ),
        Material(
            idMaterial = 6,
            urlImagenMaterial = "url_electronica.png",
            nombre = "Electrónica",
            descripcion = "Material reciclable de electrónica.",
            precio = 20.0,
            cantidad = 600,
            unidadDeMedida = "kg",
            puntosPorUnidad = 20,
            puntosAcumulados = 600,
            idComprador = 6
        ),
        Material(
            idMaterial = 7,
            urlImagenMaterial = "url_pilas.png",
            nombre = "Pilas y baterías",
            descripcion = "Material reciclable de pilas y baterías.",
            precio = 25.0,
            cantidad = 125,
            unidadDeMedida = "kg",
            puntosPorUnidad = 25,
            puntosAcumulados = 125,
            idComprador = 7
        ),
        Material(
            idMaterial = 8,
            urlImagenMaterial = "url_madera.png",
            nombre = "Madera",
            descripcion = "Material reciclable de madera.",
            precio = 7.0,
            cantidad = 140,
            unidadDeMedida = "kg",
            puntosPorUnidad = 7,
            puntosAcumulados = 140,
            idComprador = 8
        ),
        Material(
            idMaterial = 9,
            urlImagenMaterial = "url_neumaticos.png",
            nombre = "Neumáticos",
            descripcion = "Material reciclable de neumáticos.",
            precio = 18.0,
            cantidad = 90,
            unidadDeMedida = "kg",
            puntosPorUnidad = 18,
            puntosAcumulados = 90,
            idComprador = 9
        ),
        Material(
            idMaterial = 10,
            urlImagenMaterial = "url_residuos.png",
            nombre = "Residuos orgánicos",
            descripcion = "Material reciclable de residuos orgánicos.",
            precio = 4.0,
            cantidad = 200,
            unidadDeMedida = "kg",
            puntosPorUnidad = 4,
            puntosAcumulados = 200,
            idComprador = 10
        ),
        Material(
            idMaterial = 11,
            urlImagenMaterial = "url_aceites.png",
            nombre = "Aceites",
            descripcion = "Material reciclable de aceites.",
            precio = 15.0,
            cantidad = 75,
            unidadDeMedida = "L",
            puntosPorUnidad = 15,
            puntosAcumulados = 75,
            idComprador = 11
        ),
        Material(
            idMaterial = 12,
            urlImagenMaterial = "url_carton.png",
            nombre = "Cartón",
            descripcion = "Material reciclable de cartón.",
            precio = 5.0,
            cantidad = 25,
            unidadDeMedida = "kg",
            puntosPorUnidad = 5,
            puntosAcumulados = 25,
            idComprador = 12
        ),
        Material(
            idMaterial = 13,
            urlImagenMaterial = "url_vidrio.png",
            nombre = "Vidrio",
            descripcion = "Material reciclable de vidrio.",
            precio = 10.0,
            cantidad = 50,
            unidadDeMedida = "kg",
            puntosPorUnidad = 10,
            puntosAcumulados = 50,
            idComprador = 13
        )
    )


    val totalPoints = materials.sumOf { it.puntosAcumulados.toDouble() }
    val porcentajes = if (totalPoints == 0.0) {
        materials.map { 0f }.toTypedArray()
    } else {
        materials.map { (it.puntosAcumulados.toDouble() / totalPoints).toFloat() }.toTypedArray()
    }
    val colors = List(materials.size) { generateRandomColor() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Gráfico de Torta de la Distribución de Materiales con colores sincronizados
        ModernChartPie(
            porcentajes = porcentajes,
            labels = materials.map { it.nombre }.toTypedArray(),
            chartTitle = "Distribución de Materiales Reciclables",
            colors = colors
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista detallada de puntos por material con colores y porcentajes
        MaterialPointsList(materials, colors, porcentajes)
    }
}

// Función de vista previa para la pantalla de estadísticas detalladas
@Preview(showBackground = true)
@Composable
fun PreviewDetailedStatisticsScreen() {
    DetailedStatisticsScreen()
}
