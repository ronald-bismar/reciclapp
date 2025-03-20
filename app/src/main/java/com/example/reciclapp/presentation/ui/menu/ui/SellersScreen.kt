package com.example.reciclapp.presentation.ui.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.presentation.ui.menu.ui.vistas.initiateCall
import com.example.reciclapp.presentation.ui.menu.ui.vistas.openWhatsAppMessage
import com.example.reciclapp.presentation.viewmodel.UbicacionViewModel
import com.example.reciclapp.presentation.viewmodel.UserViewModel

@Composable
fun SellersScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    ubicacionViewModel: UbicacionViewModel
) {

    val context = LocalContext.current
    val sellers = userViewModel.vendedores.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(
            text = "Vendedores Cercanos",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (sellers.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                MensajeListaVaciaVendedores()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sellers) { usuario ->
                    ContactCard(
                        usuario,
                        viewProfile = {
                            navController.navigate("VendedorPerfil/${usuario.idUsuario}/${" "}")
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
}
@Composable
fun MensajeListaVaciaVendedores(){
    Text(
        text = "No se encontraron vendedores cercanos",
        fontSize = 24.sp,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

