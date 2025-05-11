package com.example.securityscanapp.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.securityscanapp.data.entities.Event
import com.example.securityscanapp.navigation.Screen
import com.example.securityscanapp.ui.theme.AppTheme
import com.example.securityscanapp.ui.theme.Black
import com.example.securityscanapp.ui.theme.Blue
import com.example.securityscanapp.ui.theme.Green
import com.example.securityscanapp.ui.theme.LightYellow
import com.example.securityscanapp.ui.theme.Red
import com.example.securityscanapp.ui.theme.White
import com.example.securityscanapp.ui.theme.Yellow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventCard(
    modifier: Modifier = Modifier,
    event : Event,
    onSeeAttendenceClick : (Int)  -> Unit,
    onEditEventClick : ()  -> Unit,
    onDeleteEventClick : ()  -> Unit,
    onRecordAttendenceClick : () -> Unit,
    navController: NavController
) {
    AppTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp)
                .padding(16.dp)
                .shadow(elevation = 10.dp,
                    shape = RoundedCornerShape(25.dp)  ,
                    clip = true)
                .combinedClickable(
                    onLongClick = onEditEventClick
                ){},
            colors = CardDefaults.cardColors(
                containerColor = LightYellow,
                contentColor = Black
            ),
            border = BorderStroke(7.dp, Yellow),
        ) {
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text=event.eventname,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 16.dp)
                    )
                    Text(text=event.eventdate,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 16.dp)
                    )
                    Text(text= event.eventtime,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.Center
                )  {
                    Button(
                        onClick = {navController.navigate(Screen.AttendanceRecords.createRoute(event.eventid))},
                        modifier = Modifier
                            .padding(8.dp)
                            .width(150.dp)
                            .height(70.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue,
                            contentColor = White
                        )
                    ) {
                        Text(text = "View",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                    Button(
                        onClick = {
                            Log.d("Navigation", "Record Attendance clicked for event: ${event.eventid}")
                            onRecordAttendenceClick()
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .width(150.dp)
                            .height(70.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green,
                            contentColor = White
                        )
                    ) {
                        Text(text = "Record",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                    Button(
                        onClick = onDeleteEventClick,
                        modifier = Modifier
                            .padding(8.dp)
                            .width(150.dp)
                            .height(70.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Red,
                            contentColor = White
                        )
                    ) {
                        Text(text = "Delete",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}