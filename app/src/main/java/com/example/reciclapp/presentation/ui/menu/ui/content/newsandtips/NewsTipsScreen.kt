package com.example.reciclapp.presentation.ui.menu.ui.content.newsandtips

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun NewsTipsScreen(api: GlobalWasteApi) {
    var newsTips by remember { mutableStateOf<List<NewsTipResponse>>(emptyList()) }
    val scope = rememberCoroutineScope()

    // Realizar la solicitud de datos a la API
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = api.getNewsTips()
                newsTips = response.results
            } catch (e: Exception) {
                // Manejo del error: Puedes mostrar un mensaje de error o loguearlo
            }
        }
    }

    // Contenedor principal de la pantalla
    Surface(modifier = Modifier.fillMaxSize()) {
        // Verificar si hay datos disponibles
        if (newsTips.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(newsTips.size) { index ->
                    NewsTipItem(newsTip = newsTips[index])
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        } else {
            // Mostrar un mensaje de carga o de error
            Box(
                contentAlignment = androidx.compose.ui.Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                BasicText(
                    text = "Cargando noticias y tips...",
                    style = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
                )
            }
        }
    }
}

@Composable
fun NewsTipItem(newsTip: NewsTipResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        BasicText(
            text = newsTip.title,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        BasicText(
            text = newsTip.content,
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        BasicText(
            text = newsTip.date,
            style = TextStyle(
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        )
    }
}
