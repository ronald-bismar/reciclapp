package com.example.reciclapp.presentation.ui.menu.ui

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.drawToBitmap
import coil.compose.rememberAsyncImagePainter
import com.example.reciclapp.R

@Composable
fun MarkerComprador(urlImagenPerfil: Uri = Uri.parse("android.resource://com.example.reciclapp/drawable/perfil")) {
    Box(modifier = Modifier.size(60.dp)) {
        // Imagen de fondo (perfil)
        Image(
            painter = rememberAsyncImagePainter(model = urlImagenPerfil),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .offset(x = 10.dp, y = 3.2.dp)
                .clip(CircleShape),
        )

        // Imagen del marcador
        Image(
            painter = painterResource(id = R.drawable.marker),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
        )
    }
}

@Preview(showBackground = false)
@Composable
fun MarkerCompradorPreview() {
    // Ejemplo de Bitmap para previsualización
    MarkerComprador(Uri.parse("android.resource://com.example.reciclapp/drawable/perfil"))
}



fun composeToBitmap(context: Context, content: @Composable () -> Unit): Bitmap {
    val composeView = ComposeView(context).apply {
        setContent(content)
        measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        layout(0, 0, measuredWidth, measuredHeight)
    }
    return composeView.drawToBitmap()
}
