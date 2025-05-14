package com.example.reciclapp_bolivia.presentation.ui.menu.ui.content.newsandtips

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun NewsTipsScreen(api: GlobalWasteApi) {
    var newsTips by remember { mutableStateOf<List<NewsTipResponse>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = api.getNewsTips() // Make sure this API method exists in your interface
                newsTips = response.results
            } catch (e: Exception) {
                Log.e("NewsTipsScreen", "Error fetching data: ${e.message}")
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        if (newsTips.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(newsTips) { newsTip ->
                    NewsTipItem(newsTip = newsTip)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                BasicText(
                    text = "Loading news and tips...",
                    style = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
                )
            }
        }
    }
}

@Composable
fun NewsTipItem(newsTip: NewsTipResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        BasicText(
            text = newsTip.title,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        BasicText(
            text = newsTip.content,
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        BasicText(
            text = newsTip.date,
            style = TextStyle(
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        )
    }
}
