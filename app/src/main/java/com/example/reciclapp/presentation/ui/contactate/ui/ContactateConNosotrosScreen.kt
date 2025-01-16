package com.example.reciclapp.presentation.ui.contactate

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reciclapp.R
import com.example.reciclapp.presentation.ui.CompartirScreen.ButtonWithIcon

@Composable
fun ContactateConNosotrosScreen() {
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
                text = "Contáctate con Nosotros",
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
                text = """
                    En NextMacroSystem nos especializamos en ofrecer soluciones tecnológicas avanzadas. 
                    Creamos software a medida, desarrollamos sitios web modernos y funcionales, 
                    aplicaciones móviles de alto rendimiento, y sistemas web personalizados que se adaptan a las necesidades de tu negocio. 
                    Si buscas innovación, calidad y un equipo comprometido con tus proyectos, ¡estás en el lugar correcto!
                    Además, nos encargamos de brindarte un excelente soporte para que tu experiencia sea la mejor. 
                    Contáctanos por nuestras redes sociales, correo electrónico, o a través de nuestro sitio web para obtener más información.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Botón para el enlace al sitio web
            Button(
                onClick = {
                    val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nextmacrosystem.com"))
                    context.startActivity(websiteIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8))
            ) {
                Text(
                    text = "Visita nuestro sitio web",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            // Botones para redes sociales y contacto
            ButtonWithIcon(
                text = "Llamar por Teléfono",
                icon = Icons.Filled.Phone,
                backgroundColor = Color(0xFF34A853),
                onClick = {
                    // Agregar el número de teléfono aquí
                    val phoneNumber = "tel:+123456789"
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber))
                    context.startActivity(intent)
                }
            )

            ButtonWithIcon(
                text = "Enviar Correo",
                icon = Icons.Filled.Email,
                backgroundColor = Color(0xFF4285F4),
                onClick = {
                    // Agregar el correo aquí
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:contacto@nextmacrosystem.com")
                    }
                    context.startActivity(Intent.createChooser(emailIntent, "Enviar correo"))
                }
            )

            // Botones para redes sociales
            SocialMediaButton(
                text = "WhatsApp",
                url = "https://wa.me/123456789",
                color = Color(0xFF25D366)
            )

            SocialMediaButton(
                text = "Facebook",
                url = "https://www.facebook.com/NextMacroSystem",
                color = Color(0xFF4267B2)
            )

            SocialMediaButton(
                text = "TikTok",
                url = "https://www.tiktok.com/@NextMacroSystem",
                color = Color(0xFFFF0050)
            )

            SocialMediaButton(
                text = "X (Twitter)",
                url = "https://twitter.com/NextMacroSystem",
                color = Color(0xFF1DA1F2)
            )

            SocialMediaButton(
                text = "LinkedIn",
                url = "https://www.linkedin.com/company/nextmacrosystem",
                color = Color(0xFF0A66C2)
            )
        }
    }
}



@Composable
fun SocialMediaButton(text: String, url: String, color: Color) {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
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
fun PreviewContactateConNosotrosScreen() {
    ContactateConNosotrosScreen()
}
