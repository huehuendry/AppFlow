package com.example.appflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * com.example.appflow.MainActivity is the entry point of the application.
 * It sets the content view to display the com.example.appflow.AppFlowMainScreen composable.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Set the main UI content to com.example.appflow.AppFlowMainScreen
            AppFlowMainScreen()
        }
    }
}

/**
 * com.example.appflow.AppFlowMainScreen is the main composable function displaying the app's UI.
 * It shows a welcome message, a button, and a counter that increments when the button is clicked.
 */
@Composable
fun AppFlowMainScreen() {
    // Counter to track the number of button clicks
    var counter by remember { mutableStateOf(0) }

    // Surface serves as the background for the UI
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Column arranges the UI elements vertically
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Display a welcome message
            Text(text = "Welcome to AppFlow!", fontSize = 24.sp)

            // Spacer for adding some vertical space
            Spacer(modifier = Modifier.height(16.dp))

            // Button to increment the counter
            Button(onClick = { counter++ }) {
                Text("Click Me")
            }

            // Spacer for adding some vertical space
            Spacer(modifier = Modifier.height(16.dp))

            // Display the current counter value
            Text("Button clicked $counter times")

        }
    }
}

/**
 * com.example.appflow.AppFlowMainScreenPreview is a preview function for com.example.appflow.AppFlowMainScreen.
 * It allows developers to see the UI in Android Studio's Compose Preview tool.
 */
@Preview(showBackground = true)
@Composable
fun AppFlowMainScreenPreview() {
    AppFlowMainScreen()
}
