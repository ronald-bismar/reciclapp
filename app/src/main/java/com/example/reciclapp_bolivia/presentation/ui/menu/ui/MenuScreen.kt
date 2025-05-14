package com.example.reciclapp_bolivia.presentation.ui.menu.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.reciclapp_bolivia.util.ItemsMenu

@Composable
fun MenuScreen(
    navController: NavHostController,
    menuItems: List<ItemsMenu>,
) {
    val currentRoute = currentRoute(navController)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(horizontal = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            menuItems.forEach { item ->
                val selected = currentRoute == item.ruta
                CustomNavigationItem(
                    selected = selected,
                    onClick = { navController.navigate(item.ruta) },
                    icon = item.icon,
                    label = item.title,
                )
            }
        }
    }
}

@Composable
fun CustomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
) {
    val size by animateDpAsState(if (selected) 48.dp else 20.dp)
    val elevation by animateDpAsState(if (selected) 10.dp else 0.dp)
    val yOffset by animateDpAsState(if (selected) (-25).dp else 0.dp)

    Box(
        modifier = Modifier
            .height(72.dp)
            .width(72.dp)
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.primary)
            .offset(y = yOffset)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .size(if (selected) size + 0.dp else size)
                    .background(
                        if (selected) MaterialTheme.colorScheme.surface else Color.Transparent,
                        CircleShape
                    ) // Color y forma de fondo
                    .padding(if (selected) 8.dp else 0.dp)
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(size),
                    tonalElevation = elevation
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.size(44.dp)
                        )
                    }
                }
            }
            Text(
                text = label,
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 0.dp),
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
