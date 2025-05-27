package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.reciclapp.R
import com.example.reciclapp.presentation.ui.registro.ui.showToast
import com.example.reciclapp.presentation.viewmodel.UserViewModel

@Composable
fun UserTypeScreen(
    userViewModel: UserViewModel,
    mainNavController: NavController,
) {

    val context = LocalContext.current
    val updateUserState by userViewModel.updateUserState.observeAsState()
    val user by userViewModel.user.observeAsState()
    val isVendedor = userViewModel.isVendedor.observeAsState().value
    var userSelected by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(updateUserState) {
        isLoading = false
        if (updateUserState?.isSuccess == true) {
            mainNavController.navigate("menu") {
                popUpTo(mainNavController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        } else if (updateUserState?.isFailure == true) {
            showToast(context, "Ocurrio un error intentalo de nuevo")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = 30.dp, horizontal = 40.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tipo de usuario",
                    style = MaterialTheme.typography.headlineMedium,

                    )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Elige el tipo de usuario con el que deseas registrarte para comprar o vender materiales de reciclaje",
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .8f)
                )
            }
        }

        TiposDeUsuario(Modifier.align(Alignment.Center)) { seleccionado, descripcionUsuario, tipoDeUsuario ->
            run {
                userSelected = seleccionado
                text = descripcionUsuario
                if (tipoDeUsuario == "comprador") userViewModel.onIsVendedorChanged(false) else userViewModel.onIsVendedorChanged(
                    true
                )
            }
        }

        if (userSelected) {
            Column(
                Modifier.align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = text, textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                ButtonUserSelected(isLoading = isLoading, saveUserType = {
                    isLoading = true
                    val updateUser =
                        user?.copy(tipoDeUsuario = if (isVendedor == true) "vendedor" else "comprador")
                    if (updateUser != null) {
                        userViewModel.updateUser(updateUser)
                    }
                })
            }
        }

    }
}

@Composable
fun ButtonUserSelected(saveUserType: () -> Unit, isLoading: Boolean) {
    Button(
        onClick = { saveUserType() },
        modifier = Modifier
            .padding(40.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp) // Ajusta la altura según sea necesario
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White, // Cambia el color si es necesario
                    modifier = Modifier.size(25.dp) // Ajusta el tamaño del indicador
                )
            } else {
                Text(text = "Continuar")
            }
        }
    }
}



@Composable
fun TiposDeUsuario(modifier: Modifier, userSelected: (Boolean, String, String) -> Unit) {

    val textVendedor = "Aquel usuario que compra material reciclable"
    val textComprador =
        "Aquel usuario que vende material reciclable que muchas veces se acumula en su domicilio"

    var elevatedComprador by remember { mutableStateOf(false) }
    var elevatedVendedor by remember { mutableStateOf(false) }

    val iconsUser = listOf(R.drawable.perfil, R.drawable.perfil)
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        CardUser(iconsUser[0], "Comprador", elevatedComprador) {
            elevatedComprador = !elevatedComprador
            elevatedVendedor = false
            userSelected(elevatedComprador, textVendedor, "comprador")
        }
        CardUser(iconsUser[1], "Reciclador", elevatedVendedor) {
            elevatedVendedor = !elevatedVendedor
            elevatedComprador = false
            userSelected(elevatedVendedor, textComprador, "vendedor")
        }
    }
}

@Composable
fun CardUser(icon: Int, type: String, isElevated: Boolean, bajarOtroCard: () -> Unit) {

    val elevation by animateDpAsState(targetValue = if (isElevated) 8.dp else 4.dp, label = "")

    val offsetY by animateDpAsState(targetValue = if (isElevated) (-50).dp else 0.dp, label = "")

    Box(
        Modifier
            .width(170.dp)
            .height(230.dp)
            .offset(y = offsetY)
            .animateContentSize()
    ) {
        Card(
            onClick = { bajarOtroCard() }, // Toggle elevation state on click
            modifier = Modifier.padding(10.dp), // Animate size changes smoothly
            elevation = CardDefaults.cardElevation(elevation), // Use animated elevation
            colors = CardDefaults.cardColors(
                containerColor = if (isElevated) MaterialTheme.colorScheme.primaryContainer else Color.White,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    color = if (isElevated) Color.White else Color.Black.copy(
                        alpha = 0.8f
                    ),
                    text = type,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = icon),
                    contentDescription = null,
                )
            }
        }
    }
}

