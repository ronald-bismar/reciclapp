package com.example.reciclapp.presentation.ui.menu.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.reciclapp.presentation.viewmodel.UserViewModel
import kotlinx.coroutines.launch

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
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        if (user != null && title != null) {
            Text(
                text = title,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            usuarios.forEach { usuario ->
                ContactCard(
                    usuario,
                    viewProfile = {
                        val profileRoute = if (user.tipoDeUsuario == "comprador") "vendedorPerfil/${usuario.idUsuario}" else "compradorPerfil/${usuario.idUsuario}"
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
