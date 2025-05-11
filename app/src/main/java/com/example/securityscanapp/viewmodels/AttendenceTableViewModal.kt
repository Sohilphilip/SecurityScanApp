package com.example.securityscanapp.viewmodels

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityscanapp.data.entities.Attendence
import com.example.securityscanapp.data.repository.EventWithAttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AttendenceTableViewModal @Inject constructor(
    val eventAttendenceRepository : EventWithAttendanceRepository) : ViewModel()
{
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _attendenceRecords = MutableStateFlow<List<Attendence>>(emptyList())
    val attendenceRecords : StateFlow<List<Attendence>> = _attendenceRecords.asStateFlow()


    fun fetchAllAttendenceRecords(eventId: Int) {
        Log.d("VM_DEBUG", "Starting fetch for event: $eventId")
        viewModelScope.launch {
            eventAttendenceRepository.getEventWithAttendance(eventId)
                .catch { e -> Log.e("VM_DEBUG", "Fetch error", e) }
                .collect { relation ->
                    Log.d("VM_DEBUG", "Received ${relation.attendees.size} records")
                    _attendenceRecords.value = relation.attendees
                }
        }
    }

    fun deleteAttendanceRecord(eventId: Int,studentId : Int){
        viewModelScope.launch {
            eventAttendenceRepository.deleteAttendenceInAnEvent(eventId,studentId)
        }
    }

    fun onSearchQueryChange(query: String, eventId: Int) {
        _searchText.value = query
        performSearch(query, eventId)
    }

    fun performSearch(query: String, eventId: Int) {
        viewModelScope.launch {
            _isSearching.value = true

            if (query.isEmpty()) {
                fetchAllAttendenceRecords(eventId) // Reset when query is empty
            } else {
                val filteredResults: List<Attendence> = when {
                    query.contains("@") -> eventAttendenceRepository.searchAttendenceByEmail(query, eventId).first()
                    query.any { it.isDigit() } && query.any { it.isLetter() } -> eventAttendenceRepository.searchAttendenceByReg(query, eventId).first()
                    else -> eventAttendenceRepository.searchAttendenceByName(query, eventId).first()
                }

                _attendenceRecords.value = filteredResults
            }

            _isSearching.value = false
        }
    }

    fun exportAttendanceToExcel(context: Context, eventId: Int) {
        viewModelScope.launch {
            try {
                val attendanceList = attendenceRecords.value
                if (attendanceList.isEmpty()) {
                    Log.d("ExcelExport", "No attendance records to export")
                    return@launch
                }

                // Create Workbook and Sheet
                val workbook = XSSFWorkbook()
                val sheet = workbook.createSheet("Attendance Records")

                // Create Header Row
                val headerRow = sheet.createRow(0)
                val headers = listOf("Name", "Registration No", "Email", "Phone")
                headers.forEachIndexed { index, title ->
                    headerRow.createCell(index).setCellValue(title)
                }

                // Populate Data Rows
                attendanceList.forEachIndexed { index, record ->
                    val row = sheet.createRow(index + 1)
                    row.createCell(0).setCellValue(record.studentname)
                    row.createCell(1).setCellValue(record.studentregno)
                    row.createCell(2).setCellValue(record.studentemail)
                    row.createCell(3).setCellValue(record.studentphone)
                }

                // Save File to Downloads Folder
                val fileName = "Attendance_Event_$eventId.xlsx"
                val filePath = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    fileName
                )

                FileOutputStream(filePath).use { fileOut ->
                    workbook.write(fileOut)
                    workbook.close()
                }

                Log.d("ExcelExport", "File saved at: ${filePath.absolutePath}")
            } catch (e: IOException) {
                Log.e("ExcelExport", "Error exporting to Excel", e)
            }
        }
    }
}
