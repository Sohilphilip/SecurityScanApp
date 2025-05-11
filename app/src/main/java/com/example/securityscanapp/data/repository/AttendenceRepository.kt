package com.example.securityscanapp.data.repository

import com.example.securityscanapp.data.dao.AttendenceDAO
import com.example.securityscanapp.data.entities.Attendence
import kotlinx.coroutines.flow.Flow

class AttendanceRepository(private val attendanceDao: AttendenceDAO) {

    /** ─── ATTENDANCE OPERATIONS ─────────────────────────── */

    // Insert or Update Attendance
    suspend fun upsertAttendance(attendance: Attendence) {
        attendanceDao.upsertAttendence(attendance)
    }

    // Delete an Attendance Record
    suspend fun deleteAttendance(attendance: Attendence) {
        attendanceDao.deleteAttendence(attendance)
    }

    // Get All Attendance Records
    fun getAllAttendance(): Flow<List<Attendence>> {
        return attendanceDao.getAllAttendence()
    }

    // Get Attendance Sorted by Registration Number
    fun getAllAttendanceByRegNo(): Flow<List<Attendence>> {
        return attendanceDao.getAllAttendenceByRegistrationNo()
    }

    // Get Attendance Sorted by Name
    fun getAllAttendanceByName(): Flow<List<Attendence>> {
        return attendanceDao.getAlllAttendenceByName()
    }

    // Get Attendance Sorted by Email
    fun getAllAttendanceByEmail(): Flow<List<Attendence>> {
        return attendanceDao.getAllAttendenceByEmail()
    }

    // Search Attendance by Email
    fun searchAttendanceByEmail(query: String): Flow<List<Attendence>> {
        return attendanceDao.searchAttendenceByEmail(query)
    }

    // Search Attendance by Registration Number
    fun searchAttendanceByRegNo(query: String): Flow<List<Attendence>> {
        return attendanceDao.searchAttendenceByRegNo(query)
    }

    // Search Attendance by Name
    fun searchAttendanceByName(query: String): Flow<List<Attendence>> {
        return attendanceDao.searchAttendenceByName(query)
    }
}
