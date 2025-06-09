package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas.initiateCall
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas.openWhatsAppMessage
import com.nextmacrosystem.reciclapp.presentation.viewmodel.UbicacionViewModel
import com.nextmacrosystem.reciclapp.presentation.viewmodel.UserViewModel

enum class BuyerFilterOption {
    ALL, PROXIMITY, RATING
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyersScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    ubicacionViewModel: UbicacionViewModel
) {
    val context = LocalContext.current
    val buyers = userViewModel.compradores.collectAsState().value
    val myLocation = ubicacionViewModel.myCurrentLocation.collectAsState().value
    val compradoresConUbicacion = ubicacionViewModel.ubicacionesConUsuarios.collectAsState().value

    var selectedFilter by remember { mutableStateOf(BuyerFilterOption.ALL) }
    var isFilterExpanded by remember { mutableStateOf(false) }

    val filteredBuyers = when (selectedFilter) {
        BuyerFilterOption.ALL -> buyers
        BuyerFilterOption.PROXIMITY -> {
            if (myLocation != null) {
                val buyersWithLocation = buyers.filter { buyer ->
                    compradoresConUbicacion.any { map ->
                        map.entries.any { (usuario, _) -> usuario.idUsuario == buyer.idUsuario }
                    }
                }

                buyersWithLocation.sortedBy { buyer ->
                    val buyerLocation = compradoresConUbicacion
                        .flatMap { it.entries }
                        .find { (usuario, _) -> usuario.idUsuario == buyer.idUsuario }
                        ?.value

                    if (buyerLocation != null) {
                        calculateDistance(
                            myLocation.latitude, myLocation.longitude,
                            buyerLocation.latitude, buyerLocation.longitude
                        )
                    } else {
                        Double.MAX_VALUE
                    }
                }
            } else {
                buyers
            }
        }

        BuyerFilterOption.RATING -> buyers.sortedByDescending { it.puntaje }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        BuyersFilterHeader(
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it },
            isExpanded = isFilterExpanded,
            onExpandedChange = { isFilterExpanded = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredBuyers.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                MensajeListaVaciaCompradores()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredBuyers) { usuarioComprador ->
                    ContactCard(
                        usuarioComprador,
                        viewProfile = {
                            navController.navigate("compradorPerfil/${usuarioComprador.idUsuario}")
                        },
                        sendMessage = {
                            openWhatsAppMessage(context, usuarioComprador.telefono.toString())
                        },
                        call = {
                            initiateCall(context, usuarioComprador.telefono.toString())
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyersFilterHeader(
    selectedFilter: BuyerFilterOption,
    onFilterSelected: (BuyerFilterOption) -> Unit,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when (selectedFilter) {
                    BuyerFilterOption.ALL -> "Todos los compradores"
                    BuyerFilterOption.PROXIMITY -> "Compradores por cercanía"
                    BuyerFilterOption.RATING -> "Compradores por calificación"
                },
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            IconButton(onClick = { onExpandedChange(!isExpanded) }) {
                Icon(Icons.Default.FilterList, contentDescription = "Filtrar")
            }
        }

        AnimatedVisibility(visible = isExpanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    FilterOption(
                        icon = Icons.Default.Person,
                        title = "Todos los compradores",
                        isSelected = selectedFilter == BuyerFilterOption.ALL,
                        onSelected = {
                            onFilterSelected(BuyerFilterOption.ALL)
                            onExpandedChange(!isExpanded)
                        }
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    FilterOption(
                        icon = Icons.Default.LocationOn,
                        title = "Por cercanía",
                        isSelected = selectedFilter == BuyerFilterOption.PROXIMITY,
                        onSelected = { onFilterSelected(BuyerFilterOption.PROXIMITY)
                            onExpandedChange(!isExpanded)}
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    FilterOption(
                        icon = Icons.Default.Star,
                        title = "Por calificación",
                        isSelected = selectedFilter == BuyerFilterOption.RATING,
                        onSelected = { onFilterSelected(BuyerFilterOption.RATING)
                            onExpandedChange(!isExpanded)}
                    )
                }
            }
        }
    }
}

@Composable
fun FilterOption(
    icon: ImageVector,
    title: String,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelected)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.6f
            )
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.weight(1f))

        if (isSelected) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun MensajeListaVaciaCompradores() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.PersonSearch,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No se encontraron compradores",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Intenta cambiar el filtro o actualizar la lista",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

// Utility function to calculate distance between two points
private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val earthRadius = 6371.0 // radius in kilometers

    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)

    val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2)

    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

    return earthRadius * c
}