package com.example.securityscanapp.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securityscanapp.ui.theme.AppTheme
import com.example.securityscanapp.ui.theme.Red
import com.example.securityscanapp.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteRecordDailogBox(modifier: Modifier = Modifier,
                    onConfirmDelete : () -> Unit,
                    onDismiss: () -> Unit)
{
    var context = LocalContext.current

    AppTheme {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(imageVector = Icons.Default.Warning ,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.padding(horizontal = 8.dp).size(30.dp))
                    Text(
                        text = "Delete Attendance!!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            },
            text = {
                Column {
                    Text(text = "Are you sure you want to delete this Attendance??")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirmDelete()
                        onDismiss()
                        Toast.makeText(context,"Attendence Deleted Successfully!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red,
                        contentColor = White
                    ))
                {
                    Text(text = "Yes", fontWeight = FontWeight.Black , fontSize = 14.sp , style = MaterialTheme.typography.labelLarge)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { onDismiss() }) {
                    Text(text = "No")
                }
            }
        )
    }
}