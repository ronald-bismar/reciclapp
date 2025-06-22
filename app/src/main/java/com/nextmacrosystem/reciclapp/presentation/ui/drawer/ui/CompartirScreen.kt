package com.nextmacrosystem.reciclapp.presentation.ui.drawer.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextmacrosystem.reciclapp.R

@Composable
fun CompartirScreen() {
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Título principal
            Text(
                text = "Comparte ReciclApp",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Imagen del logo o destacada
            Image(
                painter = painterResource(id = R.drawable.reciclapgrandesinfondo),
                contentDescription = "Logo de ReciclApp",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 16.dp)
            )

            // Descripción
            Text(
                text = "ReciclApp es una iniciativa dedicada a la promoción del reciclaje y la sostenibilidad ambiental. Únete a nosotros para hacer del reciclaje un hábito accesible para todos.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Botones para compartir
            Text(
                text = "Comparte esta aplicación por:",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Botón para compartir en WhatsApp
            ButtonWithIcon(
                text = "Compartir en WhatsApp",
                icon = Icons.Filled.Email,
                backgroundColor = Color(0xFF25D366),
                onClick = {
                    val shareText = "Descarga ReciclApp y únete al movimiento de reciclaje: https://reciclapp.com"
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        setPackage("com.whatsapp")
                        putExtra(Intent.EXTRA_TEXT, shareText)
                    }
                    context.startActivity(Intent.createChooser(intent, "Compartir vía"))
                }
            )

            // Botón para compartir en Facebook
            ButtonWithIcon(
                text = "Compartir en Facebook",
                icon = Icons.Filled.Send,
                backgroundColor = Color(0xFF4267B2),
                onClick = {
                    val shareText = "Descubre ReciclApp: https://reciclapp.com"
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, shareText)
                    }
                    context.startActivity(Intent.createChooser(intent, "Compartir en Facebook"))
                }
            )

            // Botón para compartir un enlace
            ButtonWithIcon(
                text = "Compartir enlace",
                icon = Icons.Filled.ExitToApp,
                backgroundColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    val shareText = "Descarga ReciclApp y ayuda a cuidar el medio ambiente: https://reciclapp.com"
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, shareText)
                    }
                    context.startActivity(Intent.createChooser(intent, "Compartir enlace"))
                }
            )

            // Botón para abrir en Play Store
            ButtonWithIcon(
                text = "Abrir en Play Store",
                icon = Icons.Filled.Share,
                backgroundColor = Color(0xFF4285F4),
                onClick = {
                    val playStoreUri = Uri.parse("https://play.google.com/store/apps/details?id=com.nextmacrosystem.reciclapp")
                    val intent = Intent(Intent.ACTION_VIEW, playStoreUri)
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun ButtonWithIcon(
    text: String,
    icon: ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCompartirScreen() {
    CompartirScreen()
}