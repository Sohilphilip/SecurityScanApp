package com.example.securityscanapp.screens

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.securityscanapp.data.entities.Attendence
import com.example.securityscanapp.data.entities.Event
import com.example.securityscanapp.data.models.QRData
import com.example.securityscanapp.ui.theme.Green
import com.example.securityscanapp.ui.theme.Red
import com.example.securityscanapp.ui.theme.White
import com.example.securityscanapp.viewmodels.QRDetailsViewModal
import org.json.JSONObject
import java.net.URLDecoder

@Composable
fun QRDetailsScreen(
    navController: NavController,
    viewModal: QRDetailsViewModal,
    eventId: Int,
    qrData: String,
    event: Event
) {
    val decodedData = qrData.let { URLDecoder.decode(it, "UTF-8") }
    val jsonData = remember { JSONObject(decodedData) }

    val eventName = jsonData.optString("event", "N/A")
    val eventDate = jsonData.optString("date", "N/A")
    val eventTime = jsonData.optString("time", "N/A")
    val eventLocation = jsonData.optString("location", "N/A")
    val eventRoom = jsonData.optString("room", "N/A")
    val name = jsonData.optString("name", "N/A")
    val regNo = jsonData.optString("regNo", "N/A")
    val email = jsonData.optString("email", "N/A")
    val phone = jsonData.optString("phone", "N/A")

    val context = LocalContext.current

    var validationResult by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    val qrDetails = QRData(
        eventName = eventName,
        eventDate = eventDate,
        eventTime = eventTime,
        studentName = name,
        studentReg = regNo,
        studentEmail = email,
        studentPhone = phone
    )

    LaunchedEffect(eventId) {
        validationResult = viewModal.validityCheck(
            eventId = eventId,
            qrDetail = qrDetails,
            cardEventName = event.eventname,
            cardEventDate = event.eventdate,
            cardEventTime = event.eventtime
        )
        isLoading = false
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Event Details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)), // Light Blue Background
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Event Details", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

                EventDetailRow("Event Name", eventName)
                EventDetailRow("Date", eventDate)
                EventDetailRow("Time", eventTime)
                EventDetailRow("Location", eventLocation)
                EventDetailRow("Room", eventRoom)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Student Details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)), // Light Green Background
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Student Details", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

                EventDetailRow("Student Name", name)
                EventDetailRow("Reg No", regNo)
                EventDetailRow("Email", email)
                EventDetailRow("Phone", phone)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Validity Status
        Text(
            text = if (validationResult) "VALID" else "INVALID",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(alpha = alpha)
                .background(if (validationResult) Green.copy(alpha = 0.2f) else Red.copy(alpha = 0.2f))
                .padding(12.dp),
            color = if (validationResult) Green else Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Red, contentColor = White),
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text("Go Back", fontSize = 16.sp, fontWeight = FontWeight.Black)
            }

            Button(
                onClick = {
                    if (validationResult) {
                        viewModal.upsertAttendenceData(
                            Attendence(
                                eventid = eventId,
                                studentname = name,
                                studentregno = regNo,
                                studentemail = email,
                                studentphone = phone
                            )
                        )
                        Toast.makeText(context, "Attendance Saved Successfully!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Invalid QR - Cannot Save", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = validationResult,
                colors = ButtonDefaults.buttonColors(containerColor = Green, contentColor = White),
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Text("Confirm", fontSize = 16.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
fun EventDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(value, fontSize = 14.sp)
    }
}
