<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/ui/menu/ui/PantallaPresentacion.kt
package com.example.reciclapp_bolivia.presentation.ui.menu.ui
========
package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/ui/menu/ui/PantallaPresentacion.kt

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/ui/menu/ui/PantallaPresentacion.kt
import com.example.reciclapp_bolivia.R
========
import com.nextmacrosystem.reciclapp.R
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/ui/menu/ui/PantallaPresentacion.kt
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@Composable
fun PantallaPresentacion(mainNavController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Carousel()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.55f)
                .align(Alignment.BottomCenter)
                .zIndex(1f), // La columna ocupa el 90% de la altura del Box
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            TextWithShadow()

            LogoReciclapp()

            ButtonWithBorderRadius(
                mainNavController = mainNavController,
                600,
                "presentacion app"
            )
        }

        val painterResource =
            painterResource(if (isSystemInDarkTheme()) R.drawable.abstract_shape_dark else R.drawable.abstract_shape_white)
        Image(
            painter = painterResource,
            contentDescription = "ShapeAbstract",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 20.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun LogoReciclapp() {
    var visible by remember {
        mutableStateOf(false)
    }
    val animatedAlpha by animateFloatAsState(targetValue = if (visible) 1f else 0f, label = "alpha")
    LaunchedEffect(Unit) {
        delay(2000)
        visible = true
    }

    Image(
        painter = painterResource(id = R.drawable.logo_reciclapp),
        contentDescription = "Logo reciclapp",
        modifier = Modifier
            .width(350.dp)
            .graphicsLayer { alpha = animatedAlpha }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Carousel() {
    val pagerState = rememberPagerState(initialPage = 0)
    val imageSlider = listOf(
        painterResource(id = R.drawable.img_carousel1),
        painterResource(id = R.drawable.img_carousel2),
        painterResource(id = R.drawable.img_carousel3)
    )

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(3000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
        ) {
            HorizontalPager(
                count = imageSlider.size,
                state = pagerState,
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
            ) { page ->
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                            lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }

                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                ) {
                    Image(
                        painter = imageSlider[page],
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun TextWithShadow() {
    // Controla la visibilidad animada del texto
    var visible by remember { mutableStateOf(false) }
    val textColor =
        if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

    // Animación de opacidad
    val opacity by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "animateFloatAsState" // Duración de la animación de 4 segundos
    )

    // Animación de desplazamiento
    val offsetX by animateDpAsState(
        targetValue = if (visible) 0.dp else (-50).dp,
        animationSpec = tween(durationMillis = 1000),
        label = "animateFloatAsState"// Duración de la animación de 4 segundos
    )

    // Efecto que se lanza cuando el Composable es compuesto
    LaunchedEffect(Unit) {
        // Retrasa la ejecución para que la animación comience después de un pequeño intervalo
        delay(100) // Ajusta el retraso si es necesario
        visible = true
    }

    Text(
        text = "Recicla\n y Gana",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp)
            .offset(x = offsetX), // Aplica la animación de desplazamiento vertical
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        style = TextStyle(
            color = textColor.copy(alpha = opacity), // Aplica la opacidad animada
            shadow = Shadow(
                color = Color.Gray, // Color de la sombra
                offset = Offset(4f, 4f), // Desplazamiento de la sombra
                blurRadius = 8f // Radio de desenfoque de la sombra
            )
        )
    )
}

@Composable
fun ButtonWithBorderRadius(
    mainNavController: NavController,
    durationAnimation: Long,
    nextScreen: String
) {
    var visible by remember { mutableStateOf(false) }
    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        label = "animateFloatAsState"
    )

    LaunchedEffect(Unit) {
        delay(durationAnimation)
        visible = true
    }

    // Envolver el TextButton en un Box para aplicar el alpha al fondo
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(70.dp)
            .padding(vertical = 10.dp)
            .graphicsLayer(alpha = animatedAlpha) // Aplica la animación de opacidad aquí
    ) {
        TextButton(
            onClick = {
                mainNavController.navigate(nextScreen) {
                    // Elimina todas las pantallas anteriores, incluyendo la pantalla inicial
                    popUpTo(mainNavController.graph.startDestinationId) {
                        inclusive = true
                    }
                    // Evita que el usuario regrese a esta pantalla
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary
                        )
                    ),
                    shape = RoundedCornerShape(16.dp) // Borde redondeado
                )
                .clip(RoundedCornerShape(16.dp)), // Aplica el borde redondeado al fondo y contenido
            shape = RoundedCornerShape(16.dp) // Borde redondeado en el TextButton
        ) {
            Text(
                text = "Iniciar",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}
