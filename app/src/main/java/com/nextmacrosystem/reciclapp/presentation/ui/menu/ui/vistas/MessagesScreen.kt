<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/ui/menu/ui/vistas/MessagesScreen.kt
package com.example.reciclapp_bolivia.presentation.ui.menu.ui.vistas
========
package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/ui/menu/ui/vistas/MessagesScreen.kt

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/ui/menu/ui/vistas/MessagesScreen.kt
import coil.compose.rememberAsyncImagePainter
import com.example.reciclapp_bolivia.R
import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.presentation.states.MessagesScreenState
import com.example.reciclapp_bolivia.presentation.viewmodel.MensajeViewModel
import com.example.reciclapp_bolivia.util.FechaUtils.formatChatDateTime
import com.example.reciclapp_bolivia.util.NameRoutes.CHATSCREEN

private const val TAG = "MessagesScreen"
========
import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.presentation.viewmodel.MensajeViewModel
import com.nextmacrosystem.reciclapp.util.FechaUtils.formatChatDateTime
import com.nextmacrosystem.reciclapp.util.NameRoutes.CHATSCREEN
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/ui/menu/ui/vistas/MessagesScreen.kt

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
    mensajeViewModel: MensajeViewModel,
    mainNavController: NavHostController,
) {
    val messagesWithUsers by mensajeViewModel.mensajesConUsuario.collectAsState()
    val state by mensajeViewModel.messagesScreenState.collectAsState()

    LaunchedEffect(Unit) {
        mensajeViewModel.getListOfMessagesWithUsuario()
    }
    
    LaunchedEffect(state) {
        Log.d(TAG, "MessagesScreen: $state")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mensajes") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (state is MessagesScreenState.Empty) {
                Log.d(TAG, "Empty")
                Text(
                    text = "No hay mensajes",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontSize = 16.sp
                )
            } else if (state is MessagesScreenState.Loading) {
                Log.d(TAG, "Loading")
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(40.dp),
                        strokeWidth = 3.dp
                    )
                }
            } else if (state is MessagesScreenState.Success) {
                Log.d(TAG, "Success")
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    messagesWithUsers.forEach { (usuario, mensaje) ->
                        item {
                            MessageCard(
                                usuario = usuario, // Reemplazar con datos reales del usuario
                                lastMessage = mensaje,
                                onClick = {
                                    mensajeViewModel.setUserContacted(usuario)
                                    mainNavController.navigate(CHATSCREEN)
                                }
                            )
                        }
                    }
                }
            } else if (state is MessagesScreenState.Error) {
                Log.d(TAG, "Error")
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "Ocurrio un error",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontSize = 20.sp
                    )
                    Text(
                        text = (state as MessagesScreenState.Error).error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageCard(
    usuario: Usuario,
    lastMessage: Mensaje?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Avatar del usuario
            val painter = if (usuario.urlImagenPerfil.isEmpty())
                painterResource(R.drawable.perfil)
            else
                rememberAsyncImagePainter(model = usuario.urlImagenPerfil)

            Image(
                painter = painter,
                contentDescription = "Imagen del usuario",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Nombre del usuario
                Text(
                    text = usuario.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Último mensaje
                Text(
                    text = lastMessage?.contenido ?: "No hay mensajes",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 1
                )
            }

            // Fecha del último mensaje
            Text(
                text = lastMessage?.fecha?.let { formatChatDateTime(it) } ?: "",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}