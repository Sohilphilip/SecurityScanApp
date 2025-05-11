package com.example.securityscanapp.navigation

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.securityscanapp.components.AddEventDialog
import com.example.securityscanapp.data.entities.Event
import com.example.securityscanapp.screens.AttendenceRecordsScreen
import com.example.securityscanapp.screens.DisplayEventsScreen
import com.example.securityscanapp.screens.QRDetailsScreen
import com.example.securityscanapp.screens.ScanQRScreen
import com.example.securityscanapp.viewmodels.AttendenceTableViewModal
import com.example.securityscanapp.viewmodels.EventRecordsViewmodel
import com.example.securityscanapp.viewmodels.QRDetailsViewModal

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.EventRecords.route,
        modifier = modifier
    ) {
        composable(Screen.EventRecords.route) {
            val eventRecordsViewModel: EventRecordsViewmodel = hiltViewModel()
            DisplayEventsScreen(
                viewmodel = eventRecordsViewModel,
                navController = navController
            )
        }

        composable(Screen.AddEventDialog.route) {
            val eventRecordsViewModel: EventRecordsViewmodel = hiltViewModel()
            AddEventDialog(
                viewModel = eventRecordsViewModel,
                onDismiss = { navController.popBackStack() },
                eventToEdit = null
            )
        }

        composable(
            route = Screen.AttendanceRecords.route,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: -1
            val attendenceTableViewModel: AttendenceTableViewModal = hiltViewModel()

            AttendenceRecordsScreen(
                eventId = eventId,
                navController = navController,
                viewModal = attendenceTableViewModel
            )
        }

        composable(
            route = Screen.ScanQRScreen.route,
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            Log.d("Navigation", "Opened ScanQRScreen with eventId: $eventId")
            ScanQRScreen(navController = navController, eventId = eventId)
        }


        composable(
            route = Screen.QRDetailsScreen.route,
            arguments = listOf(
                navArgument("eventId") { type = NavType.StringType },
                navArgument("qrData") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val eventIdString = backStackEntry.arguments?.getString("eventId") ?: ""
            val qrData = backStackEntry.arguments?.getString("qrData") ?: ""
            val eventId = eventIdString.toIntOrNull() ?: 0

            val viewModel: QRDetailsViewModal = hiltViewModel()
            var event by remember { mutableStateOf<Event?>(null) }

            LaunchedEffect(eventId) {
                viewModel.getEventById(eventId) { fetchedEvent ->
                    event = fetchedEvent
                }
            }

            if (event == null) {
                CircularProgressIndicator()
            } else {
                QRDetailsScreen(
                    navController = navController,
                    viewModal = viewModel,
                    eventId = eventId,
                    qrData = qrData,
                    event = event!!
                )
            }
        }
    }
}
