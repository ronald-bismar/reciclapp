package com.example.reciclapp.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reciclapp.R
import com.example.reciclapp.presentation.viewmodel.UserViewModel

@Composable
fun DrawerContent(navController: NavController, onItemClick: () -> Unit, userViewModel: UserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(0.9f)
           // .padding(16.dp)
            .background(Color.White)
    ) {
        HeaderImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(16.dp))
        Divider()
        Text("Home", modifier = Modifier.padding(16.dp))
        Text("Contact", modifier = Modifier.padding(16.dp))
        Text("Perfil", modifier = Modifier.padding(16.dp))
        Divider()
        ListItem(
            headlineContent = { Text("Item 1") },
            leadingContent = { Icon(Icons.Default.Home, contentDescription = null) },
            modifier = Modifier.clickable {
                navController.navigate("item1")
                onItemClick()
            }
        )

        ListItem(
            headlineContent = { Text("Item 2") },
            leadingContent = { Icon(Icons.Default.Home, contentDescription = null) },
            modifier = Modifier.clickable {
                navController.navigate("item2")
                onItemClick()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.reciclapgrandesinfondo),
        contentDescription = "Header",
        modifier = modifier
            .size(width = 500.dp, height = 300.dp)
    )
}
