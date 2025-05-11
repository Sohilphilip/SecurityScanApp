package com.example.securityscanapp.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.securityscanapp.R
import com.example.securityscanapp.data.entities.Event
import com.example.securityscanapp.ui.theme.AppTheme
import com.example.securityscanapp.viewmodels.EventRecordsViewmodel

@Composable
fun AddEventDialog(
    viewModel: EventRecordsViewmodel,
    eventToEdit: Event?,
    onDismiss: () -> Unit
) {
    var eventName by remember { mutableStateOf(eventToEdit?.eventname ?: "") }
    var eventDate by remember { mutableStateOf(eventToEdit?.eventdate ?: "") }
    var eventTime by remember { mutableStateOf(eventToEdit?.eventtime ?: "") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            eventDate = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            eventTime = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    AppTheme {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(if (eventToEdit == null) "Add New Event" else "Edit Event") },
            text = {
                Column {
                    OutlinedTextField(
                        value = eventName,
                        onValueChange = { eventName = it },
                        label = { Text("Event Name") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = null) }
                    )
                    OutlinedTextField(
                        value = eventDate,
                        onValueChange = { eventDate = it },
                        label = { Text("Event Date") },
                        leadingIcon = { IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                        } }
                    )
                    OutlinedTextField(
                        value = eventTime,
                        onValueChange = { eventTime = it },
                        label = { Text("Event Time") },
                        leadingIcon = { IconButton(onClick = { timePickerDialog.show() }) {
                            Icon(painter = painterResource(R.drawable.time), contentDescription = null)
                        } }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (eventName.isNotBlank() && eventDate.isNotBlank() && eventTime.isNotBlank()) {
                        if (eventToEdit == null) {
                            viewModel.insertEvent(eventName, eventDate, eventTime)
                            Toast.makeText(context, "Event Added!", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.upsertEvent(eventToEdit.copy(eventname = eventName, eventdate = eventDate, eventtime = eventTime))
                            Toast.makeText(context, "Event Updated!", Toast.LENGTH_SHORT).show()
                        }
                        onDismiss()
                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(if (eventToEdit == null) "Add Event" else "Update Event")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) { Text("Cancel") }
            }
        )
    }
}
