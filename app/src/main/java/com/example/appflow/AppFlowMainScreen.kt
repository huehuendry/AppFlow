package com.example.appflow


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AppFlowMainScreen(navController: NavController) {
    var counter by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Welcome to AppFlow!", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { counter++ }) {
                Text("Click Me")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Button clicked $counter times")
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                navController.navigate("details_screen/$counter")
            }) {
                Text("Go to Details")
            }
        }
    }
}
