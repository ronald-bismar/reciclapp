package com.example.reciclapp.presentation.ui.menu.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.presentation.viewmodel.TransaccionViewModel
import com.example.reciclapp.util.FechaUtils.formatChatDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    idTransaccion: String,
    transaccionViewModel: TransaccionViewModel
) {
    var contentNewMessage by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val scrollState = rememberLazyListState()
    val messagesBothUsers by transaccionViewModel.messagesBotUsers.collectAsState()
    val usuarioQueContacta by transaccionViewModel.usuarioContactado.collectAsState()
    val myUser by transaccionViewModel.myUser.observeAsState()
    var messageToSend: Mensaje? = null
    LaunchedEffect(Unit) {
        transaccionViewModel.getMessagesByChat(idTransaccion)
    }

    LaunchedEffect(myUser) {
        myUser?.let {
            transaccionViewModel.escucharNuevosMensajes(idTransaccion, myUser!!.idUsuario)
        }
    }

    LaunchedEffect(messagesBothUsers.size) {
        if (messagesBothUsers.isNotEmpty()) {
            scrollState.animateScrollToItem(0) // Scroll al último mensaje
            messageToSend = messagesBothUsers.findLast { it.idEmisor == myUser?.idUsuario }
            val idUsuarioQueContacta =
                messagesBothUsers.findLast { it.idEmisor != myUser?.idUsuario }?.idEmisor

            idUsuarioQueContacta?.let {
                withContext(Dispatchers.IO) { transaccionViewModel.getUserForTransaction(it) }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("User") },
            navigationIcon = {
                IconButton(onClick = { }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
            },
            actions = {
                IconButton(onClick = { /* Opciones */ }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
                }
            }
        )

        HorizontalDivider()

        LazyColumn(
            modifier = Modifier.weight(1f),
            state = scrollState,
            reverseLayout = true,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(messagesBothUsers.reversed()) { index, message ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut()
                ) {
                    MessageBubble(
                        message = message,
                        isCurrentUser = message.idEmisor == myUser?.idUsuario
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = contentNewMessage,
                onValueChange = { contentNewMessage = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe un mensaje...") },
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        Log.d("ChatScreen", "onSend1")
                        sendMessage(contentNewMessage, {}, focusManager)
                        contentNewMessage = ""
                    }
                ),
                trailingIcon = {
                    AnimatedVisibility(visible = contentNewMessage.isNotBlank()) {
                        IconButton(
                            onClick = {
                                Log.d("ChatScreen", "Click") // Es null no esta pasando de aqui

                                messageToSend?.let {
                                    it.copy(
                                        contenido = contentNewMessage
                                    )
                                    transaccionViewModel.sendMessage(it,
                                        usuarioQueContacta?.tokenNotifications ?: ""
                                    )
                                    sendMessage(contentNewMessage, { }, focusManager)
                                    contentNewMessage = ""
                                }

                            },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Enviar",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageBubble(
    message: Mensaje,
    isCurrentUser: Boolean
) {
    val bubbleColor = if (isCurrentUser) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!isCurrentUser) {
            // Avatar del otro usuario
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Contacto",
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 4.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Column(
            horizontalAlignment = if (isCurrentUser) Alignment.End else Alignment.Start
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = bubbleColor),
                shape = RoundedCornerShape(
                    topStart = if (isCurrentUser) 16.dp else 0.dp,
                    topEnd = if (isCurrentUser) 0.dp else 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = message.contenido,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = formatChatDateTime(message.fecha), // Usar fecha real
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }
    }
}

private fun sendMessage(
    message: String,
    onSend: (String) -> Unit,
    focusManager: FocusManager
) {
    if (message.isNotBlank()) {
        onSend(message)
        focusManager.clearFocus()
    }
}
