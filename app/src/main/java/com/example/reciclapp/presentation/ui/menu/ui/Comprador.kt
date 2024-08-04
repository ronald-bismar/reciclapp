package com.example.reciclapp.presentation.ui.menu.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.reciclapp.R
import com.example.reciclapp.domain.entities.Comentario
import com.example.reciclapp.domain.entities.Material
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.presentation.viewmodel.CompradoresViewModel

private const val REQUEST_CALL_PERMISSION = 1

@Composable
fun Comprador(
    navController: NavController,
    compradorId: Int,
    compradoresViewModel: CompradoresViewModel = hiltViewModel()
) {
    LaunchedEffect(compradorId) {
        compradoresViewModel.apply {
            fetchCompradorById(compradorId)
            fetchMaterialesByComprador(compradorId)
            fetchComentariosByComprador(compradorId)
        }
    }

    val selectedComprador = compradoresViewModel.selectedComprador.collectAsState().value
    val materiales = compradoresViewModel.materiales.collectAsState().value
    val comentarios = compradoresViewModel.comentarios.collectAsState().value

    selectedComprador?.let { comprador ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ProfileHeader(comprador.urlImagenPerfil)
            ProfileDetails3(comprador)
            ActionButtons(comprador.telefono.toString())
            SectionTitle("Objetos comprables:")
            MaterialList(materiales)
            SectionTitle("Comentarios:")
            ComentariosList(comentarios)
        }
    } ?: Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ProfileHeader(imageUrl: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePicture3(painter = rememberAsyncImagePainter(model = imageUrl), size = 100.dp)
        }
    }
}

@Composable
fun ProfilePicture3(painter: Painter, size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun ProfileDetails3(comprador: Usuario) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        SectionTitle("Account Details")
        ProfileItem3("Name", comprador.nombre)
        ProfileItem3("LastName", comprador.apellido)
        ProfileItem3("Phone", comprador.telefono.toString())
        ProfileItem3("Address", comprador.direccion)
        ProfileItem3("Email", comprador.correo)
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun SectionTitle(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

@Composable
fun ProfileItem3(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Text(text = value, fontSize = 16.sp)
    }
}

@Composable
fun ActionButtons(phoneNumber: String) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButton3("Mensaje", Icons.Default.Email) { openWhatsAppMessage(context, phoneNumber) }
        ActionButton3("Llamar", Icons.Default.Call) { initiateCall(context, phoneNumber) }
    }
    Spacer(modifier = Modifier.height(10.dp))
    Divider()
}

@Composable
fun ActionButton3(label: String, icon: ImageVector, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.height(40.dp)) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}

fun initiateCall(context: Context, phoneNumber: String) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
        try {
            context.startActivity(intent)
        } catch (e: SecurityException) {
            Toast.makeText(
                context,
                "No se puede realizar la llamada: permiso no concedido",
                Toast.LENGTH_SHORT
            ).show()
        }
    } else {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.CALL_PHONE),
            REQUEST_CALL_PERMISSION
        )
    }
}

fun openWhatsAppMessage(context: Context, phoneNumber: String) {
    val uri = Uri.parse("https://wa.me/591$phoneNumber")
    val intent = Intent(Intent.ACTION_VIEW, uri).apply { setPackage("com.whatsapp") }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}

@Composable
fun ComentariosList(comentarios: List<Comentario>) {
    Column {
        comentarios.forEach {
            CardComentario(it)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun MaterialList(materiales: List<Material>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        materiales.forEach {
            Text(
                "${it.nombre}: ${it.monedaDeCompra} ${it.precio} por ${it.unidadDeMedida}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun CardComentario(item: Comentario) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.perfil),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                item.nombreUsuarioQueComento,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Text("Puntuacion: ${item.puntuacion}", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Text(
            item.contenidoComentario,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Text(
            item.fechaComentario,
            style = MaterialTheme.typography.labelSmall,
            color = Color.LightGray
        )
    }
}
