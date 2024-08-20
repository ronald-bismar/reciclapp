package com.example.reciclapp.presentation.ui.menu.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reciclapp.R

@Preview(showBackground = true)
@Composable
fun UserTypeScreen() {
    var userSelected by remember { mutableStateOf(false) }
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
                    text = "Elige el tipo de usuario",
                    style = MaterialTheme.typography.headlineMedium,

                    )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Elige el tipo de usuario con el que te buscarán para comprar o vender materiales de reciclaje",
                    textAlign = TextAlign.Justify, color = Color.Black.copy(alpha = .8f)
                )
            }
        }

        TiposDeUsuario(Modifier.align(Alignment.Center)) { it -> userSelected = it }

        if (userSelected)
            ButtonUserSelected(Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun ButtonUserSelected(modifier: Modifier) {
    Button(
        onClick = {}, modifier = modifier
            .padding(40.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Continuar")
    }
}


@Composable
fun TiposDeUsuario(modifier: Modifier, userSelected: (Boolean) -> Unit) {

    var elevatedComprador by remember { mutableStateOf(false) }
    var elevatedVendedor by remember { mutableStateOf(false) }

    val iconsUser = listOf(R.drawable.perfil, R.drawable.perfil)
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        CardUser(iconsUser[0], "Comprador", elevatedComprador) {
            elevatedComprador = !elevatedComprador
            elevatedVendedor = false
            userSelected(elevatedComprador)
        }
        CardUser(iconsUser[1], "Vendedor", elevatedVendedor) {
            elevatedVendedor = !elevatedVendedor
            elevatedComprador = false
            userSelected(elevatedVendedor)
        }
    }

}


@Composable
fun CardUser(icon: Int, type: String, isElevated: Boolean, bajarOtroCard: () -> Unit) {

    val elevation by animateDpAsState(targetValue = if (isElevated) 4.dp else 0.dp, label = "")

    val offsetY by animateDpAsState(targetValue = if (isElevated) (-50).dp else 0.dp, label = "")

    Box(
        Modifier
            .width(170.dp)
            .height(220.dp)
            .offset(y = offsetY) // Apply the animated vertical offset
            .animateContentSize()
    ) {
        Card(
            onClick = { bajarOtroCard() }, // Toggle elevation state on click
            modifier = Modifier.padding(10.dp), // Animate size changes smoothly
            elevation = CardDefaults.cardElevation(elevation), // Use animated elevation
            colors = CardDefaults.cardColors(
                containerColor = if (isElevated) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
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
                    color = if (isElevated) Color.White else MaterialTheme.colorScheme.onSurface.copy(
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

