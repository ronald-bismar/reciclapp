package com.example.reciclapp.presentation.ui.menu.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.reciclapp.presentation.viewmodel.UserViewModel

@Composable
fun MenuScreen(
    navController: NavHostController,
    menuItems: List<ItemsMenu>,
    userViewModel: UserViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(47.dp)
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
            menuItems.forEachIndexed { index, item ->
                val selected = currentRoute(navController) == item.ruta
                CustomNavigationItem(
                    selected = selected,
                    onClick = { navController.navigate(item.ruta) },
                    icon = item.icon,
                    label = item.title,
                    isFirst = index == 0,
                    isLast = index == menuItems.size - 1
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
    isFirst: Boolean = false,
    isLast: Boolean = false
) {
    val size by animateDpAsState(if (selected) 38.dp else 20.dp)
    val elevation by animateDpAsState(if (selected) 10.dp else 0.dp)
    val yOffset by animateDpAsState(if (selected) (-20).dp else 0.dp)

    val backgroundColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Box(
        modifier = Modifier
            .height(47.dp)
            .width(72.dp)
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .size(if (selected) size + 10.dp else size)
                    .offset(y = yOffset)
                    .background(if (selected) Color.White else Color.Transparent, CircleShape)
                    .padding(if (selected) 5.dp else 0.dp)
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(size)
                        .align(Alignment.Center),
                    tonalElevation = elevation
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = contentColor,
                            modifier = Modifier.size(44.dp)
                        )
                    }
                }
            }
            Text(
                text = label,
                color = contentColor,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = if (selected) 19.dp else 4.dp),
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
