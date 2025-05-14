package com.example.reciclapp_bolivia.presentation.ui.menu.ui.vistas.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.reciclapp.R

@Composable
fun HeaderImageLogoReciclapp(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.reciclapgrandesinfondo),
        contentDescription = "Header",
        modifier = modifier
    )
}