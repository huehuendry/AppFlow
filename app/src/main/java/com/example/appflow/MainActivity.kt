package com.example.appflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.navArgument
import com.example.appflow.model.BottomNavItem
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * MainActivity is the entry point of the application. It sets up the navigation and displays
 * the main screen.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppFlowNavigation()
        }
    }
}

val bottomNavItems = listOf(
    BottomNavItem("main_screen", "Home", Icons.Filled.Home),
    BottomNavItem("about_screen", "About", Icons.Filled.Info ),
    BottomNavItem("details_screen", "Details", Icons.AutoMirrored.Filled.List)
)

@Composable
fun AppFlowNavigation() {
    // Create a NavController to manage the navigation
    val navController = rememberNavController()

    // Scaffold to hold the BottomNavigation bar
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        // Apply the innerPadding to the NavHost modifier
        NavHost(
            navController = navController,
            startDestination = "splash_screen",
            modifier = Modifier.padding(innerPadding), // Use innerPadding here
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) }
        ) {
            composable("splash_screen") {
                SplashScreen(navController = navController)
            }
            // Main screen composable
            composable("main_screen") {
                AppFlowMainScreen(navController)
            }
            // About screen composable
            composable("about_screen") {
                AboutScreen()
            }
            // Details screen composable
            composable(
                route = "details_screen/{counter}",
                arguments = listOf(navArgument("counter") { type = NavType.IntType })
            ) { backStackEntry ->
                val counter = backStackEntry.arguments?.getInt("counter") ?: 0
                DetailsScreen(navController, counter)
            }
        }
    }
}




@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val currentRoute = currentRoute(navController)

        bottomNavItems.forEach { navItem ->
            NavigationBarItem(
                label = { Text(navItem.label) },
                selected = currentRoute == navItem.route,
                onClick = {
                    // Navigate to the selected screen
                    if (navItem.route == "details_screen") {
                        // Navigate to details_screen with a default counter value (e.g., 0)
                        navController.navigate("details_screen/0") {
                            popUpTo("main_screen") { inclusive = false }
                        }
                    } else {
                        navController.navigate(navItem.route) {
                            popUpTo("main_screen") { inclusive = false }
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label,
                        tint = if (currentRoute == navItem.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun AppFlowMainScreen(navController: NavController) {
    var counter by remember { mutableStateOf(0) }

    // Surface with a background color
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Welcome message
            Text(text = "Welcome to AppFlow!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            // Button to increment the counter
            Button(onClick = { counter++ }) {
                Text("Click Me")
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Display the current value of the counter
            Text("Button clicked $counter times", fontSize = 18.sp)

            Spacer(modifier = Modifier.height(24.dp))
            // Button to navigate to the details screen with the current counter value
            Button(onClick = {
                navController.navigate("details_screen/$counter")
            }) {
                Text("Go to Details")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavController, counter: Int) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Details Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text("You clicked the button $counter times!", fontSize = 18.sp)
        }
    }
}

@Composable
fun AboutScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "About Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text("This is the About Screen!", fontSize = 18.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    // Get the ViewModel instance
    val viewModel: SplashViewModel = viewModel()

    // Collect the readiness state from the ViewModel
    val isReady by viewModel.isReady.collectAsState()

    // Navigate to the main screen when resources are ready
    LaunchedEffect(isReady) {
        if (isReady) {
            navController.navigate("main_screen") {
                popUpTo("splash_screen") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.bg_splash_stars), // Replace with your resource
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "AppFlow",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        )
    }
}


// ViewModel for managing resource loading
class SplashViewModel : ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady

    init {
        viewModelScope.launch {
            // Simulate resource loading
            delay(3000)
            _isReady.value = true
        }
    }
}



/**
 * Preview function for the main screen and details screen.
 * It allows you to preview the UI components in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun AppFlowNavigationPreview() {
    AppFlowNavigation()
}

//@Preview(showBackground = true)
//@Composable
//fun AppFlowMainScreenPreview() {
//    AppFlowMainScreen()
//}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    val mockNavController = rememberNavController()
    DetailsScreen(navController = mockNavController, counter = 5)
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen()
}
