package com.nextmacrosystem.reciclapp.presentation.animations

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay

/**
 * [UserTypeAnimated] es un composable que muestra una fila de pestañas (Tabs) animada para seleccionar entre "Comprador" y "Vendedor".
 *
 * @param onIsVendedorChanged Callback que se invoca cuando se selecciona la pestaña de "Vendedor".
 *
 * Este composable gestiona la selección de pestañas con una animación diferida que aplica un retraso de 300 ms antes de cambiar
 * la pestaña seleccionada visualmente.
 */
@Composable
fun UserTypeAnimated(isVendedor: Boolean = false,onIsVendedorChanged: (Boolean) -> Unit) {
    Log.d("vendedor", "isVendedor: $isVendedor")
    // Índice de la pestaña seleccionada actualmente
    var selectedTabIndex by remember { mutableIntStateOf(if (isVendedor) 1 else 0) }
    // Índice de la pestaña pendiente de ser seleccionada (para la animación)
    var pendingTabIndex by remember { mutableStateOf<Int?>(null) }

    // Efecto lanzado cuando hay un cambio en el índice de la pestaña pendiente
    LaunchedEffect(pendingTabIndex) {
        pendingTabIndex?.let { tabIndex ->
            delay(300) // Retraso de 300 ms antes de cambiar la pestaña seleccionada
            selectedTabIndex = tabIndex
            pendingTabIndex = null
        }
    }

    // Fila de pestañas (Tabs) con un indicador animado personalizado
    TabRow(
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .background(
                        MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(30.dp) // Esquina redondeada del indicador
                    )
                    .fillMaxHeight()
                    .zIndex(-1f)
            )
        },
        divider = {}, // Se omite el divisor entre las pestañas
    ) {
        // Pestaña "Comprador"
        Tab(
            selected = selectedTabIndex == 0,
            onClick = {
                pendingTabIndex = 0 // Establece la pestaña pendiente en "Comprador"
                onIsVendedorChanged(false) // Notifica que el usuario seleccionó "Comprador"
            },
            text = {
                Text(
                    "Comprador",
                    color = if (selectedTabIndex == 0) Color.White else Color.Gray,
                    style = TextStyle(
                        fontSize = 20.sp, // Tamaño de la fuente del texto
                        fontWeight = FontWeight.Bold // Peso de la fuente del texto
                    ),
                )
            },
        )
        // Pestaña "Vendedor"
        Tab(
            selected = selectedTabIndex == 1,
            onClick = {
                pendingTabIndex = 1 // Establece la pestaña pendiente en "Vendedor"
                onIsVendedorChanged(true) // Notifica que el usuario seleccionó "Vendedor"
            },
            text = {
                Text(
                    "Reciclador",
                    color = if (selectedTabIndex == 1) Color.White  else Color.Gray,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
            },
        )
    }
}
