package com.example.reciclapp.presentation.ui.menu.ui.vistas

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.presentation.ui.registro.ui.showToast
import com.example.reciclapp.presentation.viewmodel.CompradoresViewModel
import androidx.core.net.toUri

private const val REQUEST_CALL_PERMISSION = 1

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Comprador(
    mainNavController: NavController,
    compradorId: String,
    compradoresViewModel: CompradoresViewModel
) {
    LaunchedEffect(compradorId) {
        compradoresViewModel.apply {
            fetchCompradorById(compradorId)
            fetchMaterialesByComprador(compradorId)
            fetchComentariosByComprador(compradorId)
        }
    }

    val selectedComprador = compradoresViewModel.selectedComprador.observeAsState().value
    val materiales = compradoresViewModel.productos.collectAsState().value
    val comentarios = compradoresViewModel.comentarios.collectAsState().value

    val stateNewComment by compradoresViewModel.stateNewComment.observeAsState()

    val context = LocalContext.current

    LaunchedEffect(stateNewComment) {
        if (stateNewComment?.isSuccess == true) {
            showToast(context, "Comentario añadido")
        } else if (stateNewComment?.isFailure == true) {
            showToast(context, "No se pudo subir el comentario")
        }

        compradoresViewModel.resetState()
    }

    selectedComprador?.let { comprador ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileHeader(comprador.urlImagenPerfil)
            ProfileDetails3(comprador)
            ActionButtons(comprador.telefono.toString())
            SectionTitle("Productos que compra:")
            MaterialList(materiales)
            SectionTitle("Comentarios:")
            PuntuarUsuario(onComentarioCreado = { newComment, puntuacion ->
                compradoresViewModel.enviarComentario(newComment, puntuacion)
            })
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
fun PuntuarUsuario(
    onComentarioCreado: (String, Int) -> Unit
) {
    var comentarioTexto by remember { mutableStateOf("") }
    var puntuacion by remember { mutableIntStateOf(0) }
    var showCreateComment by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessHigh
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            contentAlignment = Alignment.Center
        ) {
            ActionButton3(
                label = if (showCreateComment) "Cerrar" else "Puntuar Usuario",
                icon = Icons.Default.Star,
                onClick = { showCreateComment = !showCreateComment }
            )
        }

        AnimatedVisibility(
            visible = showCreateComment,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column {
                TextField(
                    value = comentarioTexto,
                    onValueChange = { comentarioTexto = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Escribe un comentario") },
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Puntuación:")

                // Estrellas para la puntuación
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (i in 1..5) {
                        Icon(
                            imageVector = if (i <= puntuacion) Icons.Rounded.Star else Icons.Rounded.Star,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { puntuacion = i },
                            tint = if (i <= puntuacion) Color.Yellow else Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (comentarioTexto.isNotBlank() && puntuacion > 0) {
                            onComentarioCreado(comentarioTexto, puntuacion)
                            comentarioTexto = ""
                            puntuacion = 0
                            showCreateComment = false
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Puntuar")
                }
            }
        }
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
            ProfilePicture3(painter = rememberAsyncImagePainter(model = imageUrl), size = 150.dp)
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
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
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
        ProfileItem3("Puntos", comprador.puntaje.toString())
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider()
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
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
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
    val uri = "https://wa.me/591$phoneNumber".toUri()
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
fun MaterialList(materiales: List<ProductoReciclable>) {
    if (materiales.isEmpty())
        Text(
            "Aun no hay productos registrados que compra este usuario",
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
        )
    else
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            materiales.forEach {
                val precio: String = if (it.precio < 1) "${it.precio}0"
                else it.precio.toInt().toString()
                Text(
                    "${it.nombreProducto}: ${it.monedaDeCompra} $precio por ${it.unidadMedida}",
                    style = MaterialTheme.typography.bodyLarge
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
        HorizontalDivider(modifier = Modifier.fillMaxSize())
    }
}
