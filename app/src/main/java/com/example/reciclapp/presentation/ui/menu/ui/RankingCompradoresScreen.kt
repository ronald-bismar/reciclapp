package com.example.reciclapp.presentation.ui.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.presentation.viewmodel.CompradoresViewModel
import com.example.reciclapp.util.NombreNivelUsuario

@Composable
fun RankingCompradoresScreen(
    viewModel: CompradoresViewModel = hiltViewModel()
) {
    val compradores by viewModel.compradores.collectAsState()
    val currentUser by viewModel.myUser.observeAsState()
    var userRank by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(compradores, currentUser) {
        userRank = compradores.indexOfFirst { it.idUsuario == currentUser?.idUsuario }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchCompradores()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        // Top 3 Podium
        if (compradores.isNotEmpty()) {
            TopThreeSection(compradores.take(3))
        }

        // Current User Position Card (if user is a buyer)
        currentUser?.let { user ->
            if (userRank != null && userRank!! >= 0) {
                CurrentUserRankCard(user, userRank!! + 1)
            }
        }

        // Full Ranking List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            itemsIndexed(compradores) { index, comprador ->
                RankingItem(
                    comprador = comprador,
                    position = index + 1,
                    isCurrentUser = comprador.idUsuario == currentUser?.idUsuario
                )
            }
        }
    }
}

@Composable
fun RankingHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = "Ranking de Recicladores",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun TopThreeSection(topThree: List<Usuario>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        // Second Place
        if (topThree.size > 1) {
            PodiumItem(topThree[1], 2, 0.8f)
        }
        // First Place
        if (topThree.isNotEmpty()) {
            PodiumItem(topThree[0], 1, 1f)
        }
        // Third Place
        if (topThree.size > 2) {
            PodiumItem(topThree[2], 3, 0.6f)
        }
    }
}

@Composable
fun PodiumItem(usuario: Usuario, position: Int, scale: Float) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        AsyncImage(
            model = usuario.urlImagenPerfil,
            contentDescription = "Profile picture",
            modifier = Modifier
                .size((80 * scale).dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = when(position) {
                1 -> "\uD83E\uDD47"
                2 -> "\uD83E\uDD48"
                else -> "\uD83E\uDD49"
            },
            fontSize = 24.sp
        )
        Text(
            text = usuario.nombre,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = NombreNivelUsuario.obtenerNombreNivel(usuario.puntaje),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun CurrentUserRankCard(user: Usuario, position: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tu posición actual",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "#$position",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun RankingItem(
    comprador: Usuario,
    position: Int,
    isCurrentUser: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrentUser)
                MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$position",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(40.dp)
            )
            AsyncImage(
                model = comprador.urlImagenPerfil,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = comprador.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = NombreNivelUsuario.obtenerNombreNivel(comprador.puntaje),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = "${comprador.puntaje} pts",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}