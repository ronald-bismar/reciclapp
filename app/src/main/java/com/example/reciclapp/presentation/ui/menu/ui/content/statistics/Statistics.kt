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
import com.example.reciclapp.domain.entities.ProductoReciclable
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
fun MaterialPointsList(materials: List<ProductoReciclable>, colors: List<Color>, porcentajes: Array<Float>) {
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
                        text = material.nombreProducto,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    // Puntos por Unidad
                    Text(
                        text = "${material.puntosPorCompra} p/u",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    // Puntos Acumulados
                    Text(
                        text = "${material.puntosPorCompra} puntos",
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

                totalPoints += material.puntosPorCompra
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
        ProductoReciclable(
            idProducto = 1,
            urlImagenProducto = "url_plasticos.png",
            nombreProducto = "Plásticos",
            descripcionProducto = "Material reciclable de plásticos.",
            precio = 10.0,
            cantidad = 500,
            unidadMedida = "kg",
            puntosPorCompra = 10,
            idUsuario = 1
        ),
        ProductoReciclable(
            idProducto = 2,
            urlImagenProducto = "url_metales.png",
            nombreProducto = "Metales",
            descripcionProducto = "Material reciclable de metales.",
            precio = 15.0,
            cantidad = 750,
            unidadMedida = "kg",
            puntosPorCompra = 15,
            idUsuario = 2
        ),
        ProductoReciclable(
            idProducto = 3,
            urlImagenProducto = "url_papeleria.png",
            nombreProducto = "Papelería",
            descripcionProducto = "Material reciclable de papelería.",
            precio = 5.0,
            cantidad = 300,
            unidadMedida = "kg",
            puntosPorCompra = 5,
            idUsuario = 3
        ),
        ProductoReciclable(
            idProducto = 4,
            urlImagenProducto = "url_vidrieria.png",
            nombreProducto = "Vidriería",
            descripcionProducto = "Material reciclable de vidriería.",
            precio = 8.0,
            cantidad = 400,
            unidadMedida = "kg",
            puntosPorCompra = 8,
            idUsuario = 4
        ),
        ProductoReciclable(
            idProducto = 5,
            urlImagenProducto = "url_textiles.png",
            nombreProducto = "Textiles",
            descripcionProducto = "Material reciclable de textiles.",
            precio = 12.0,
            cantidad = 240,
            unidadMedida = "kg",
            puntosPorCompra = 12,
            idUsuario = 5
        ),
        ProductoReciclable(
            idProducto = 6,
            urlImagenProducto = "url_electronica.png",
            nombreProducto = "Electrónica",
            descripcionProducto = "Material reciclable de electrónica.",
            precio = 20.0,
            cantidad = 600,
            unidadMedida = "kg",
            puntosPorCompra = 20,
            idUsuario = 6
        ),
        ProductoReciclable(
            idProducto = 7,
            urlImagenProducto = "url_pilas.png",
            nombreProducto = "Pilas y baterías",
            descripcionProducto = "Material reciclable de pilas y baterías.",
            precio = 25.0,
            cantidad = 125,
            unidadMedida = "kg",
            puntosPorCompra = 25,
            idUsuario = 7
        ),
        ProductoReciclable(
            idProducto = 8,
            urlImagenProducto = "url_madera.png",
            nombreProducto = "Madera",
            descripcionProducto = "Material reciclable de madera.",
            precio = 7.0,
            cantidad = 140,
            unidadMedida = "kg",
            puntosPorCompra = 7,
            idUsuario = 8
        ),
        ProductoReciclable(
            idProducto = 9,
            urlImagenProducto = "url_neumaticos.png",
            nombreProducto = "Neumáticos",
            descripcionProducto = "Material reciclable de neumáticos.",
            precio = 18.0,
            cantidad = 90,
            unidadMedida = "kg",
            puntosPorCompra = 18,
            idUsuario = 9
        ),
        ProductoReciclable(
            idProducto = 10,
            urlImagenProducto = "url_residuos.png",
            nombreProducto = "Residuos orgánicos",
            descripcionProducto = "Material reciclable de residuos orgánicos.",
            precio = 4.0,
            cantidad = 200,
            unidadMedida = "kg",
            puntosPorCompra = 4,
            idUsuario = 10
        ),
        ProductoReciclable(
            idProducto = 11,
            urlImagenProducto = "url_aceites.png",
            nombreProducto = "Aceites",
            descripcionProducto = "Material reciclable de aceites.",
            precio = 15.0,
            cantidad = 75,
            unidadMedida = "L",
            puntosPorCompra = 15,
            idUsuario = 11
        ),
        ProductoReciclable(
            idProducto = 12,
            urlImagenProducto = "url_carton.png",
            nombreProducto = "Cartón",
            descripcionProducto = "Material reciclable de cartón.",
            precio = 5.0,
            cantidad = 25,
            unidadMedida = "kg",
            puntosPorCompra = 5,
            idUsuario = 12
        ),
        ProductoReciclable(
            idProducto = 13,
            urlImagenProducto = "url_vidrio.png",
            nombreProducto = "Vidrio",
            descripcionProducto = "Material reciclable de vidrio.",
            precio = 10.0,
            cantidad = 50,
            unidadMedida = "kg",
            puntosPorCompra = 10,
            idUsuario = 13
        )
    )

    val totalPoints = materials.sumOf { it.puntosPorCompra.toDouble() }
    val porcentajes = if (totalPoints == 0.0) {
        materials.map { 0f }.toTypedArray()
    } else {
        materials.map { (it.puntosPorCompra.toDouble() / totalPoints).toFloat() }.toTypedArray()
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
            labels = materials.map { it.nombreProducto }.toTypedArray(),
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
