package com.example.securityscanapp.screens

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.securityscanapp.components.AttendanceRecordRowCard
import com.example.securityscanapp.components.DeleteRecordDailogBox
import com.example.securityscanapp.components.SearchBar
import com.example.securityscanapp.data.entities.Attendence
import com.example.securityscanapp.ui.theme.AppTheme
import com.example.securityscanapp.ui.theme.Green
import com.example.securityscanapp.ui.theme.Titlebar
import com.example.securityscanapp.ui.theme.White
import com.example.securityscanapp.viewmodels.AttendenceTableViewModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendenceRecordsScreen(modifier: Modifier = Modifier,
                            navController: NavController,
                            eventId : Int,
                            viewModal: AttendenceTableViewModal) {

    // Add this LaunchedEffect block
    LaunchedEffect(key1 = eventId) {
        viewModal.fetchAllAttendenceRecords(eventId)
    }

    var searchText = viewModal.searchText.collectAsState()
    var isSearching = viewModal.isSearching.collectAsState()
    var attendenceList = viewModal.attendenceRecords.collectAsState()

    var showDeleteDailogBox by remember{ mutableStateOf(false) }

    var selectedRecord by remember { mutableStateOf<Attendence?>(null)}

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                viewModal.exportAttendanceToExcel(context, eventId)
                Toast.makeText(context,"Attendence Sheet Downloaded and Saved in Downloads folder",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    AppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Attendance Information",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 8.dp) // Adds spacing between icon and title
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Titlebar,
                        titleContentColor = White,
                        navigationIconContentColor = White
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = {navController.popBackStack()},
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Titlebar,
                                contentColor = White
                            ),
                            modifier = Modifier
                                .size(32.dp) // Smaller button to prevent overflow
                                .clip(CircleShape)
                                .border(1.dp, White, CircleShape)
                                .padding(horizontal = 8.dp) // Prevents overflow at edges
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = White,
                                modifier = Modifier.size(20.dp) // Ensure icon fits well inside the button
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                SearchBar(
                    query = searchText.value,
                    onQueryChange = { query -> viewModal.onSearchQueryChange(query, eventId) }
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    val attendenceRecords = attendenceList.value

                    if(attendenceRecords.isEmpty()){
                        item{ Text(text = "No attendence records founf for this event", fontSize = 20.sp , fontWeight = FontWeight.Bold) }
                    }else{
                        items(attendenceRecords){
                                attendee ->
                            AttendanceRecordRowCard(
                                attendee = attendee,
                                onDelete = {
                                    selectedRecord = attendee
                                    showDeleteDailogBox = true
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green,
                            contentColor = White
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .width(250.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        shape = RectangleShape
                    ) {
                        Text(
                            text = "Download Excel",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    if(showDeleteDailogBox){
        DeleteRecordDailogBox(
            modifier = Modifier,
            onConfirmDelete = {
                viewModal.deleteAttendanceRecord(selectedRecord!!.eventid,selectedRecord!!.studentid)
                showDeleteDailogBox = false
            },
            onDismiss = {
                showDeleteDailogBox = false
            }
        )
    }
}
