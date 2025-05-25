package com.example.reciclapp_bolivia.presentation.ui.menu.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reciclapp_bolivia.R


@Composable
fun PresentacionAppScreen(mainNavController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = 50.dp)
        ) {
            LogoReciclapp()
        }
        Box(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {

            val painterResource =
                painterResource(if (isSystemInDarkTheme()) R.drawable.abstract_background_dark else R.drawable.abstract_background_white)
            Image(
                painter = painterResource,
                contentDescription = "ShapeAbstract",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()

            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .align(Alignment.Center)
                    .padding(top = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                TextDescriptionApp()
                ButtonWithBorderRadius(mainNavController, 600, "introduction screen")
            }
        }
    }
}

@Composable
fun TextDescriptionApp() {
    Text(
        text = "Reciclapp es una aplicacion que te permite conectar con compradores y vendedores de reciclaje que se encuentran cerca de tu zona y buscar los mejores precios para el material reciclable que tienes en casa",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier
            .padding(horizontal = 26.dp),
        textAlign = TextAlign.Justify
    )
}