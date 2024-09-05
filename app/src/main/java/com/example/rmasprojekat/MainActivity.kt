package com.example.rmasprojekat

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rmasprojekat.ds.OglasDS
import com.example.rmasprojekat.ds.UserDS
import com.example.rmasprojekat.repositories.OglasRepository
import com.example.rmasprojekat.repositories.UserRepository
import com.example.rmasprojekat.screens.AddPlace
import com.example.rmasprojekat.screens.HistoryAddPlaceScreen
import com.example.rmasprojekat.screens.Landing
import com.example.rmasprojekat.screens.Login
import com.example.rmasprojekat.screens.MainScreen
import com.example.rmasprojekat.screens.Register
import com.example.rmasprojekat.screens.ProfileScreen
import com.example.rmasprojekat.screens.SalesListScreen
import com.example.rmasprojekat.screens.SavedSalesScreen
import com.example.rmasprojekat.screens.UserListScreen
import com.example.rmasprojekat.screens.ViewSaleScreen
import com.example.rmasprojekat.services.NearbyService
import com.example.rmasprojekat.ui.ViewSaleVM
import com.example.rmasprojekat.ui.ViewSaleVMFactory
import com.example.rmasprojekat.ui.theme.RMASProjekatTheme
import com.google.firebase.FirebaseApp
import android.Manifest
import androidx.annotation.RequiresApi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.POST_NOTIFICATIONS"
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf("android.permission.POST_NOTIFICATIONS"),
                    1
                )
            }
        }

        FirebaseApp.initializeApp(this)
        setContent {
            Navigation()

        }
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Navigation() {

    val context = LocalContext.current
    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (!allGranted) {
            Toast.makeText(context, "Permissions denied", Toast.LENGTH_SHORT).show()
        }
    }

    val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS // If your app targets Android 13+
    )

    val permissionStatus = requiredPermissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    LaunchedEffect(Unit) {
        if (!permissionStatus) {
            permissionsLauncher.launch(requiredPermissions)
        }
    }
    val viewSaleVM: ViewSaleVM =
        viewModel(factory = ViewSaleVMFactory(oglasRep = OglasRepository(oglasDS = OglasDS())))
    val navController = rememberNavController()

    val painterUserLoc = painterResource(id = R.drawable.usrloc)
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
                viewSaleVM = viewSaleVM,
                painterUsr = painterUserLoc,
                userRep = UserRepository(userds = UserDS())
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
                onNavigateWhenLogout = { navController.popBackStack("landing", false) },
                onNavigateToHistory = { navController.navigate("history") },
                onNavigateToSaved = { navController.navigate("saved") }
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
                oglasRepository = OglasRepository(oglasDS = OglasDS()),
                onSaleClick = {
                    navController.popBackStack("main", false)
                    navController.navigate("viewSale")
                },
                viewSaleVM = viewSaleVM,
                userRepository = UserRepository(userds = UserDS())
            )
        }
        composable("viewSale") {
            ViewSaleScreen(
                onNavigateToMain = { navController.popBackStack("main", false) },
                vwModel = viewSaleVM
            )
        }
        composable("history") {
            HistoryAddPlaceScreen(
                onNavigateToProfile = { navController.popBackStack("profile", false) },
                oglasRep = OglasRepository(oglasDS = OglasDS())
            )
        }
        composable("saved") {
            SavedSalesScreen(
                onNavigateToProfile = { navController.popBackStack("profile", false) },
                oglasRep = OglasRepository(oglasDS = OglasDS()),
                onSaleClick = {
                    navController.popBackStack("main", false)
                    navController.navigate("viewSale")
                },
                viewSaleVM = viewSaleVM
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