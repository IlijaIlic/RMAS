package com.example.rmasprojekat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rmasprojekat.screens.AddPlace
import com.example.rmasprojekat.screens.Landing
import com.example.rmasprojekat.screens.Login
import com.example.rmasprojekat.screens.MainScreen
import com.example.rmasprojekat.screens.Register
import com.example.rmasprojekat.ui.theme.RMASProjekatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()

        }
    }
}


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "landing") {
        composable("landing") {
            Landing(onNavigateToLogin = { navController.navigate("login") },
                onNavigateToRegister = { navController.navigate("register") })
        }
        composable("login") {
            Login(onNavigateToRegister = {
                navController.popBackStack("landing", false)
                navController.navigate("register")
            },
                onNavigateToLanding = { navController.popBackStack("landing", false) },
                onNavigateToMain = { navController.navigate("main") }
            )
        }
        composable("register") {
            Register(onNavigateToLogin = {
                navController.popBackStack("landing", false)
                navController.navigate("login")
            },
                onNavigateToLanding = { navController.popBackStack("landing", false) },
                onNavigateToMain = { navController.navigate("main") }
            )
        }
        composable("main") {
            MainScreen(
                onNavigateToAddPlace = { navController.navigate("addPlace") },
                onNavigateToLanding = { navController.popBackStack("landing", false) })
        }
        composable("addPlace") {
            AddPlace(onNavigateToMain = { navController.popBackStack("main", false) })
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RMASProjekatTheme {
        Greeting("Android")
    }
}