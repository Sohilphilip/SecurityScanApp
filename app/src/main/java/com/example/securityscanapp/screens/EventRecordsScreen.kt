package com.example.securityscanapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.securityscanapp.components.AddEventDialog
import com.example.securityscanapp.components.DeleteAlertBox
import com.example.securityscanapp.components.EventCard
import com.example.securityscanapp.components.SearchBar
import com.example.securityscanapp.data.entities.Event
import com.example.securityscanapp.navigation.Screen
import com.example.securityscanapp.ui.theme.AppTheme
import com.example.securityscanapp.ui.theme.LightGreen
import com.example.securityscanapp.ui.theme.Titlebar
import com.example.securityscanapp.ui.theme.White
import com.example.securityscanapp.viewmodels.EventRecordsViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayEventsScreen(
    viewmodel: EventRecordsViewmodel,
    navController: NavController
) {
    val searchtext = viewmodel.searchText.collectAsState()
    val isSearching = viewmodel.isSearching.collectAsState()
    val eventlist = viewmodel.events.collectAsState()

    var showDailogBox by remember { mutableStateOf(false) }
    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    AppTheme {
        Scaffold(
            topBar = { TopAppBar(
                title = { Text(text = "Attendance Sheets", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Titlebar, titleContentColor = White)
            ) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        selectedEvent = null
                        showDailogBox = true
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Icon")
                }
            },
            containerColor = LightGreen
        ) { innerPadding ->
            Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                SearchBar(query = searchtext.value, onQueryChange = { viewmodel.onSearchQueryChange(it) })

                if (isSearching.value) {
                    CircularProgressIndicator(modifier = Modifier.fillMaxWidth().wrapContentWidth(
                        Alignment.CenterHorizontally))
                } else {
                    LazyColumn(contentPadding = PaddingValues(16.dp)) {
                        val eventList = eventlist.value

                        if (eventList.isEmpty()) {
                            item { Text(text = "No events found", fontSize = 18.sp, fontWeight = FontWeight.Bold) }
                        } else {
                            items(eventList) { event ->
                                EventCard(
                                    event = event,
                                    onRecordAttendenceClick = {
                                        Log.d("Navigation", "Record Attendance clicked for event: ${event.eventid}")
                                        navController.navigate(Screen.ScanQRScreen.createRoute(event.eventid.toString()))
                                    },
                                    onSeeAttendenceClick = { eventId ->
                                        navController.navigate(Screen.AttendanceRecords.createRoute(eventId))
                                    },
                                    onEditEventClick = {
                                        selectedEvent = event
                                        showDailogBox = true
                                    },
                                    onDeleteEventClick = {
                                        selectedEvent = event
                                        showDeleteDialog = true
                                    },
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDailogBox) {
        AddEventDialog(viewModel = viewmodel,
            onDismiss = { showDailogBox = false }
            , eventToEdit = selectedEvent)
    }

    if (showDeleteDialog) {
        DeleteAlertBox(
            eventName = selectedEvent!!.eventname,
            onConfirmDelete = {
                selectedEvent?.let { viewmodel.deleteEvent(it) }
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }
}

