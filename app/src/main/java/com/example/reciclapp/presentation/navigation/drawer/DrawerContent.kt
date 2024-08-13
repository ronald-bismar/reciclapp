package com.example.reciclapp.presentation.navigation.drawer

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reciclapp.R
import com.example.reciclapp.presentation.viewmodel.UserViewModel

@Composable
fun DrawerContent(mainNavController: NavController, onItemClick: () -> Unit, userViewModel: UserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight()
            .background(Color.White)
            .padding(16.dp)
    ) {
        HeaderImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        DrawerItem(mainNavController, "home", Icons.Default.Home, onItemClick)
        DrawerItem(mainNavController, "contact", Icons.Default.Call, onItemClick)
        DrawerItem(mainNavController, "perfil", Icons.Default.Person, onItemClick)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        DrawerItem(mainNavController, "item1", Icons.Default.Home, onItemClick)
        DrawerItem(mainNavController, "item2", Icons.Default.Home, onItemClick)
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
            .padding(vertical = 8.dp)
    )
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.reciclapgrandesinfondo),
        contentDescription = "Header",
        modifier = modifier
            .size(200.dp)
            .padding(16.dp)
    )
}
