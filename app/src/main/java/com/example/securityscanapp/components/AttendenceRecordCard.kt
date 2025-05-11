package com.example.securityscanapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securityscanapp.data.entities.Attendence
import com.example.securityscanapp.ui.theme.AppTheme
import com.example.securityscanapp.ui.theme.Black

@Composable
fun RowScope.TableCell(text: String, weight: Float) {
    AppTheme {
        Box(
            modifier = Modifier
                .weight(weight)
                .drawBehind {
                    // Draw top border
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 1f
                    )
                    // Draw bottom border
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1f
                    )
                }
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 8.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1, // Limits the text to one line
                overflow = TextOverflow.Ellipsis ,
                color = Black// Adds "..." if the text overflows
            )
        }
    }
}

@Composable
fun AttendanceRecordRowCard(
    attendee: Attendence,
    onDelete: (Attendence) -> Unit
) {
    AppTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
                .border(1.dp, Color.Black)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TableCell(text = attendee.studentname, weight = 1f)
            TableCell(text = attendee.studentregno, weight = 1f)
            TableCell(text = attendee.studentemail, weight = 1f)
            TableCell(text = attendee.studentphone, weight = 1f)

            // Delete Button
            IconButton(
                onClick = { onDelete(attendee) },
                modifier = Modifier
                    .size(32.dp) // Increased size for better visibility
                    .clip(RoundedCornerShape(4.dp))
                    .width(15.dp)
                    .height(15.dp)
                    .background(Color.Red)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }
    }
}



