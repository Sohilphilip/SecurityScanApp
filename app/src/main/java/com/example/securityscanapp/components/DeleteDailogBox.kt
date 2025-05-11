package com.example.securityscanapp.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securityscanapp.ui.theme.AppTheme

@Composable
fun DeleteAlertBox(
    eventName: String,
    onConfirmDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    var inputName by remember { mutableStateOf("") }
    val context = LocalContext.current  // Get context for Toast

    AppTheme {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(imageVector = Icons.Default.Warning ,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.padding(horizontal = 8.dp).size(50.dp))
                    Text(
                        text = "Delete ${eventName} and its Attendance!!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Are you sure you want to delete this event along with all attendance records?",
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Enter Event Name to Confirm Deletion:",
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    OutlinedTextField(
                        value = inputName,
                        onValueChange = { inputName = it },
                        label = { Text(text = "Event Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = null) }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (inputName == eventName) {
                            Log.d("DeleteEvent", "Attempting to delete event: $eventName")
                            onConfirmDelete()  // Call deletion function
                            Toast.makeText(context, "Deleted \"$eventName\" successfully!", Toast.LENGTH_SHORT).show()
                            onDismiss()  // Close dialog
                        } else {
                            Toast.makeText(context, "Please enter the correct event name", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    enabled = inputName.isNotBlank() // Only enable if text is entered
                ) {
                    Text(text = "Delete", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { onDismiss() }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}


