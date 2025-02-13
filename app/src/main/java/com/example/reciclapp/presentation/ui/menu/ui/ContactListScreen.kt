package com.example.reciclapp.presentation.ui.menu.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.reciclapp.presentation.ui.menu.ui.vistas.initiateCall
import com.example.reciclapp.presentation.ui.menu.ui.vistas.openWhatsAppMessage
import com.example.reciclapp.presentation.viewmodel.UserViewModel

@Composable
fun ContactListScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val user = userViewModel.user.observeAsState().value
    val context = LocalContext.current

    LaunchedEffect(user) {
        user?.let {
            if (it.tipoDeUsuario == "comprador")
                userViewModel.fetchVendedores()
            else userViewModel.fetchCompradores()
        }
    }

    val usuarios = if (user?.tipoDeUsuario == "comprador") {
        userViewModel.vendedores.collectAsState().value
    } else {
        userViewModel.compradores.collectAsState().value
    }

    Log.d("ContactListScreen", "Vendedores: ${userViewModel.vendedores.collectAsState().value}")
    Log.d("ContactListScreen", "Compradores: ${userViewModel.compradores.collectAsState().value}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(
            text =
            if (user?.tipoDeUsuario == "comprador")
                "Vendedores Cercanos"
            else "Compradores Cercanos",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (usuarios.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(usuarios) { usuario ->
                    ContactCard(
                        usuario,
                        viewProfile = {
                            val profileRoute = if (user!!.tipoDeUsuario == "comprador") {
                                "vendedorPerfil/${usuario.idUsuario}"
                            } else {
                                "compradorPerfil/${usuario.idUsuario}"
                            }
                            Log.d("ProfileRoute", "Comprador: $profileRoute")

                            navController.navigate(profileRoute)
                        },
                        sendMessage = {
                            openWhatsAppMessage(context, usuario.telefono.toString())
                        },
                        call = {
                            initiateCall(context, usuario.telefono.toString())
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun mensajeListaVacia(textoUsuario: String){
        Text(
            text = "No se encontraron $textoUsuario cercanos",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}
