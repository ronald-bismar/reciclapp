package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.nextmacrosystem.reciclapp.R

@Composable
fun HeaderImageLogoReciclapp(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.reciclapgrandesinfondo),
        contentDescription = "Header",
        modifier = modifier
    )
}