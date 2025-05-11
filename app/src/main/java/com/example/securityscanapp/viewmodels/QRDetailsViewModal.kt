package com.example.securityscanapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityscanapp.data.entities.Attendence
import com.example.securityscanapp.data.entities.Event
import com.example.securityscanapp.data.models.QRData
import com.example.securityscanapp.data.repository.EventWithAttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class QRDetailsViewModal  @Inject constructor(
    val eventWithAttendanceRepository: EventWithAttendanceRepository,
) : ViewModel() {

    // Add this new function for duplicate check
    private suspend fun isDuplicateAttendee(eventId: Int, qrDetail: QRData): Boolean {
        return eventWithAttendanceRepository.checkForDuplicates(
            eventId = eventId,
            name = qrDetail.studentName,
            regNo = qrDetail.studentReg,
            email = qrDetail.studentEmail,
            phone = qrDetail.studentPhone
        )
    }

    // Modified validity check with all conditions
    suspend fun validityCheck(
        eventId: Int,
        qrDetail: QRData,
        cardEventName: String,
        cardEventDate: String,
        cardEventTime: String
    ): Boolean {
        return isNameValid(cardEventName, qrDetail.eventName) &&
                isDateValid(cardEventDate, qrDetail.eventDate) &&
                isTimeValid(cardEventTime, qrDetail.eventTime) &&
                !isDuplicateAttendee(eventId, qrDetail)
    }

    fun getEventById(eventId: Int, onResult: (Event?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { // Use IO dispatcher
            try {
                val event = eventWithAttendanceRepository.fetchEventByEventId(eventId)
                withContext(Dispatchers.Main) {
                    onResult(event)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onResult(null)
                }
            }
        }
    }

    fun upsertAttendenceData(attendee: Attendence){
        viewModelScope.launch {
            eventWithAttendanceRepository.upsetAttendenceInAnEvent(attendee)
        }
    }

    private fun isNameValid(scannedEventName: String, eventNameFromCard: String): Boolean {
        val lowerScanned = scannedEventName.lowercase(Locale.getDefault())
        val lowerEventCard = eventNameFromCard.lowercase(Locale.getDefault())

        return lowerEventCard.contains(lowerScanned) || lowerScanned.contains(lowerEventCard)
    }

    private fun isDateValid(scannedEventDate: String, eventDateFromCard: String): Boolean {
        return try {
            val qrDate = parseDate(scannedEventDate)
            val eventDate = parseDate(eventDateFromCard)
            qrDate == eventDate
        } catch (e: Exception) {
            false
        }
    }

    private fun parseDate(dateString: String): LocalDate {
        return try {
            // Try ISO format first (2025-02-13)
            LocalDate.parse(dateString)
        } catch (e: DateTimeParseException) {
            // Try day/month/year format (13/2/2025)
            val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")
            LocalDate.parse(dateString, formatter)
        }
    }


    private fun parseTimeToMinutes(time: String): Int {
        val parts = time.split(":")
        val hours = parts[0].toInt()
        val minutes = parts.getOrNull(1)?.toInt() ?: 0
        return hours * 60 + minutes
    }

    // Improved time validation with ±2 hour window
    private fun isTimeValid(eventTime: String, qrTime: String): Boolean {
        return try {
            val eventMinutes = parseTimeToMinutes(eventTime)
            val qrMinutes = parseTimeToMinutes(qrTime)
            Math.abs(eventMinutes - qrMinutes) <= 120 // ±2 hours
        } catch (e: Exception) {
            false
        }
    }
}