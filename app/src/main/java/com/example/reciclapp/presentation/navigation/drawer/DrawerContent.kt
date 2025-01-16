package com.example.reciclapp.presentation.navigation.drawer

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reciclapp.R
import com.example.reciclapp.presentation.viewmodel.UserViewModel

@Composable
fun DrawerContent(mainNavController: NavController, onItemClick: () -> Unit) {
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

        DrawerItem(mainNavController, "Calificar", Icons.Default.Star, onItemClick)

       DrawerItem(mainNavController, "Ajustes", Icons.Default.Settings, onItemClick)

        DrawerItem(mainNavController, "perfil", Icons.Default.Person, onItemClick)

        // Texto
        Text(
            text = "NextMacroSystem 2024-2025",

            modifier = Modifier.padding(top = 46.dp, bottom = 16.dp, start = 16.dp, end = 16.dp) // Espaciado superior
        )
    }
}

@Composable
fun DrawerItem(navController: NavController, route: String, icon: ImageVector, onItemClick: () -> Unit) {
    val context = LocalContext.current
    ListItem(
        headlineContent = { Text(route.capitalize()) },
        leadingContent = { Icon(icon, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Toast.makeText(context, "$route clicked", Toast.LENGTH_SHORT).show()
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

