package com.example.securityscanapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.securityscanapp.data.dao.AttendenceDAO
import com.example.securityscanapp.data.dao.EventDAO
import com.example.securityscanapp.data.dao.EventWithAttendancesDao
import com.example.securityscanapp.data.entities.Attendence
import com.example.securityscanapp.data.entities.Event

@Database(entities = [Event::class, Attendence::class], version = 1, exportSchema = true)
abstract class AttendanceDatabase : RoomDatabase() {
    abstract fun attendanceDao(): AttendenceDAO
    abstract fun eventDao(): EventDAO
    abstract fun eventAttendenceDao(): EventWithAttendancesDao
}

