package com.example.rmasprojekat

import android.content.Intent
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rmasprojekat.ds.OglasDS
import com.example.rmasprojekat.ds.UserDS
import com.example.rmasprojekat.repositories.OglasRepository
import com.example.rmasprojekat.repositories.UserRepository
import com.example.rmasprojekat.screens.AddPlace
import com.example.rmasprojekat.screens.Landing
import com.example.rmasprojekat.screens.Login
import com.example.rmasprojekat.screens.MainScreen
import com.example.rmasprojekat.screens.Register
import com.example.rmasprojekat.screens.ProfileScreen
import com.example.rmasprojekat.screens.SalesListScreen
import com.example.rmasprojekat.screens.UserListScreen
import com.example.rmasprojekat.screens.ViewSaleScreen
import com.example.rmasprojekat.ui.ViewSaleVM
import com.example.rmasprojekat.ui.theme.RMASProjekatTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            Navigation()

        }
    }
}


@Composable
fun Navigation() {
    val viewSaleVM: ViewSaleVM = viewModel()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "landing") {
        composable("landing") {
            Landing(onNavigateToLogin = { navController.navigate("login") },
                onNavigateToRegister = { navController.navigate("register") },
                userRep = UserRepository(userds = UserDS()),
                onNavigateToMain = { navController.navigate("main") })
        }
        composable("login") {
            Login(
                onNavigateToRegister = {
                    navController.popBackStack("landing", false)
                    navController.navigate("register")
                },
                onNavigateToLanding = { navController.popBackStack("landing", false) },
                onNavigateToMain = { navController.navigate("main") },
                userRep = UserRepository(userds = UserDS())
            )
        }
        composable("register") {
            Register(
                onNavigateToLogin = {
                    navController.popBackStack("landing", false)
                    navController.navigate("login")
                },
                onNavigateToLanding = { navController.popBackStack("landing", false) },
                onNavigateToMain = { navController.navigate("main") },
                userRep = UserRepository(userds = UserDS()),
            )
        }
        composable("main") {
            MainScreen(
                onNavigateToAddPlace = { navController.navigate("addPlace") },
                onNavigateToLanding = { navController.popBackStack("landing", false) },
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToUserList = { navController.navigate("listOfUsers") },
                onNavigateToViewSale = { navController.navigate("viewSale") },
                oglasRepository = OglasRepository(oglasDS = OglasDS()),
                viewSaleVM = viewSaleVM
            )
        }
        composable("addPlace") {
            AddPlace(
                onNavigateToMain = { navController.popBackStack("main", false) },
                oglasiRep = OglasRepository(oglasDS = OglasDS())
            )
        }
        composable("profile") {
            ProfileScreen(
                onNavigateToMain = {
                    navController.popBackStack("main", false)
                },
                userRep = UserRepository(userds = UserDS()),
                onNavigateWhenLogout = { navController.popBackStack("landing", false) }
            )
        }
        composable("listOfUsers") {
            UserListScreen(
                onNavigateToMain = { navController.popBackStack("main", false) },
                onNavigateToSales = {
                    navController.popBackStack("main", false)
                    navController.navigate("listOfSales")
                },
                userRep = UserRepository(userds = UserDS())
            )
        }
        composable("listOfSales") {
            SalesListScreen(
                onNavigateToMain = { navController.popBackStack("main", false) },
                onNavigateToUsers = {
                    navController.popBackStack("main", false)
                    navController.navigate("listOfUsers")
                },
                oglasRepository = OglasRepository(oglasDS = OglasDS())
            )
        }
        composable("viewSale") {
            ViewSaleScreen(
                onNavigateToMain = { navController.popBackStack("main", false) },
                vwModel = viewSaleVM
            )
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