package com.asdec.hauling

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.asdec.hauling.data.AppDatabase
import com.asdec.hauling.data.HaulingRepository
import com.asdec.hauling.ui.MainViewModel
import com.asdec.hauling.ui.AdminViewModel
import com.asdec.hauling.ui.ViewModelFactory
import com.asdec.hauling.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = HaulingRepository(
            appDao = database.appDao(),
            userDao = database.userDao()
        )
        val viewModelFactory = ViewModelFactory(repository)

        setContent {
            val navController = rememberNavController()
            val mainViewModel: MainViewModel = viewModel(factory = viewModelFactory)
            val adminViewModel: AdminViewModel = viewModel(factory = viewModelFactory)

            val currentUser by mainViewModel.currentUser.collectAsState()

            LaunchedEffect(currentUser) {
                if (currentUser == null) {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }

            val permissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (!isGranted) {
                    Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show()
                }
            }

            Scaffold { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "login",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    // Login Screen
                    composable("login") {
                        LoginScreen(
                            viewModel = mainViewModel,
                            onLoginSuccess = {
                                navController.navigate("dashboard") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // Dashboard Screen
                    composable("dashboard") {
                        DashboardScreen(
                            viewModel = mainViewModel,
                            onNavigateToAddTruck = { batchId ->
                                navController.navigate("add_truck/$batchId")
                            },
                            onNavigateToDetails = { batchId ->
                                navController.navigate("details/$batchId")
                            },
                            onNavigateToAdmin = {
                                navController.navigate("admin_users")
                            }
                        )
                    }

                    // Admin User Management
                    composable("admin_users") {
                        AdminUserScreen(
                            viewModel = adminViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    // --- STEP 1: ADD TRUCK (ENTRANCE) ---
                    composable(
                        route = "add_truck/{batchId}",
                        arguments = listOf(navArgument("batchId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val batchId = backStackEntry.arguments?.getString("batchId") ?: ""
                        AddTruckScreen(
                            batchId = batchId,
                            viewModel = mainViewModel,
                            onBack = { navController.popBackStack() },
                            onNavigateToCamera = { type ->
                                when (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.CAMERA)) {
                                    PackageManager.PERMISSION_GRANTED -> {
                                        navController.navigate("camera/$batchId/$type")
                                    }
                                    else -> {
                                        permissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                }
                            }
                        )
                    }

                    // --- STEP 2: EXIT TRUCK (FINISH ENTRY) ---
                    // This is the new screen where you input dimensions for an existing truck
                    composable(
                        route = "exit_truck/{truckId}/{batchId}",
                        arguments = listOf(
                            navArgument("truckId") { type = NavType.LongType },
                            navArgument("batchId") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val truckId = backStackEntry.arguments?.getLong("truckId") ?: 0L
                        val batchId = backStackEntry.arguments?.getString("batchId") ?: ""

                        ExitTruckScreen(
                            truckId = truckId,
                            batchId = batchId,
                            viewModel = mainViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    // Truck Details / History Screen
                    composable(
                        route = "details/{batchId}",
                        arguments = listOf(navArgument("batchId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val batchId = backStackEntry.arguments?.getString("batchId") ?: ""

                        LaunchedEffect(batchId) {
                            mainViewModel.loadTruckDetails(batchId)
                            // Load active trucks so they can be selected for "Exit"
                            mainViewModel.loadInProgressTrucks(batchId)
                        }

                        TruckDetailsScreen(
                            batchId = batchId,
                            viewModel = mainViewModel,
                            onBack = { navController.popBackStack() },
                            onNavigateToExit = { truckId ->
                                navController.navigate("exit_truck/$truckId/$batchId")
                            }
                        )
                    }

                    // Camera Screen
                    composable(
                        route = "camera/{batchId}/{photoType}",
                        arguments = listOf(
                            navArgument("batchId") { type = NavType.StringType },
                            navArgument("photoType") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val bId = backStackEntry.arguments?.getString("batchId") ?: ""
                        val pType = backStackEntry.arguments?.getString("photoType") ?: ""

                        CameraScreen(
                            batchId = bId,
                            photoType = pType,
                            onImageCaptured = { imagePath ->
                                mainViewModel.updatePhotoPath(bId, pType, imagePath)
                                navController.popBackStack()
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}