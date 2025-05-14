package com.example.reciclapp_bolivia.presentation.navigation.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reciclapp.R
import com.example.reciclapp_bolivia.presentation.ui.registro.ui.showToast
import com.example.reciclapp_bolivia.presentation.viewmodel.UserViewModel

@Composable
fun DrawerContent(
    mainNavController: NavController,
    onItemClick: () -> Unit,
    userViewModel: UserViewModel
) {

    val logOutState by userViewModel.logOutState.observeAsState()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
            .padding(10.dp)
    ) {
        HeaderImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(5.dp))
        HorizontalDivider()
        DrawerItem(mainNavController, "Que es ReciclApp", Icons.Default.Info, onItemClick)

        DrawerItem(mainNavController, "Mision y vision", Icons.Default.Face, onItemClick)

        DrawerItem(mainNavController, "Sobre Nosotros", Icons.Default.Home, onItemClick)

        DrawerItem(mainNavController, "Compartir", Icons.Default.Share, onItemClick)

        HorizontalDivider(modifier = Modifier.padding(vertical = 5.dp))

        DrawerItem(mainNavController, "Contactate con nosotros", Icons.Default.Call, onItemClick)

        DrawerItem(mainNavController, "Calificanos", Icons.Default.Star, onItemClick)

        DrawerItem(mainNavController, "Ajustes", Icons.Default.Settings, onItemClick)

        DrawerItem(mainNavController, "perfil", Icons.Default.Person, onItemClick)

        DrawerItem(mainNavController, "login", Icons.Default.Logout, {
            userViewModel.logOutUser()
        })

        // Texto
        Text(
            text = "NextMacroSystem 2024-2025",

            modifier = Modifier.padding(
                top = 46.dp,
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp
            ) // Espaciado superior
        )
    }

    // Maneja el estado de logout
    logOutState?.let { result ->
        when {
            result.isSuccess -> {
                mainNavController.navigate("login") {
                    popUpTo("menu") {
                        inclusive = true
                    }
                }
            }

            result.isFailure -> {
                showToast(context, "No se pudo salir de la cuenta")
            }
        }
        userViewModel.resetStateLogout()
    }
}

@Composable
fun DrawerItem(
    navController: NavController,
    route: String,
    icon: ImageVector,
    onItemClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(route.capitalize()) },
        leadingContent = { Icon(icon, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Toast.makeText(context, "$route clicked", Toast.LENGTH_SHORT).show()
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                onItemClick()
            }
            .padding(1.dp, 0.dp, 1.dp, 0.dp)
    )
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        // Imagen
        Image(
            painter = painterResource(id = R.drawable.reciclapgrandesinfondo),
            contentDescription = "Header",
            modifier = Modifier
                .size(200.dp)
                .padding(1.dp, 0.dp, 1.dp, 0.dp)
        )
    }
}

