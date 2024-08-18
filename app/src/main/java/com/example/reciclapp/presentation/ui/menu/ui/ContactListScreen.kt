package com.example.reciclapp.presentation.ui.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.reciclapp.presentation.ui.menu.ui.vistas.initiateCall
import com.example.reciclapp.presentation.ui.menu.ui.vistas.openWhatsAppMessage
import com.example.reciclapp.presentation.viewmodel.UserViewModel

@Composable
fun ContactListScreen(navController: NavController, viewModel: UserViewModel = hiltViewModel()) {
    val user = viewModel.user.observeAsState().value
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(user) {
        user?.let {
            if (it.tipoDeUsuario == "comprador") viewModel.fetchVendedores() else viewModel.fetchCompradores()
        }
    }

    val title = when (user?.tipoDeUsuario) {
        "comprador" -> "Vendedores Cercanos"
        else -> "Compradores Cercanos"
    }

    val usuarios = when (user?.tipoDeUsuario) {
        "comprador" -> viewModel.vendedores.collectAsState().value
        else -> viewModel.compradores.collectAsState().value
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        if (user != null) {
            Text(
                text = title,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (usuarios.isEmpty()) {
                // Mostrar CircularProgressIndicator cuando la lista esté vacía
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                usuarios.forEach { usuario ->
                    ContactCard(
                        usuario,
                        viewProfile = {
                            val miTipoDeUsuario = user.tipoDeUsuario
                            val profileRoute =
                                when (miTipoDeUsuario) {
                                    "comprador" -> "compradorPerfil/${usuario.idUsuario}" //vamos a pantalla perfil del comprador
                                    else -> "vendedorPerfil/${usuario.idUsuario}" //vamos a pantalla perfil del vendedor
                                }
                            navController.navigate(profileRoute)
                        },
                        sendMessage = {
                            openWhatsAppMessage(context = context, usuario.telefono.toString())
                        },
                        call = {
                            initiateCall(context = context, usuario.telefono.toString())
                        }
                    )
                }
            }
        }
    }
}
