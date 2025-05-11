package com.example.securityscanapp.data.repository

import android.util.Log
import com.example.securityscanapp.data.dao.EventWithAttendancesDao
import com.example.securityscanapp.data.entities.Attendence
import com.example.securityscanapp.data.entities.Event
import com.example.securityscanapp.data.entities.EventAttendenceRelation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventWithAttendanceRepository @Inject constructor(private val eventWithAttendancesDao: EventWithAttendancesDao) {

    /** ─── ONE-TO-MANY RELATION ─────────────────────────── */

    // Fetch Event with All Attendances
    fun getEventWithAttendance(eventId: Int): Flow<EventAttendenceRelation> {
        Log.d("DB_DEBUG", "Fetching attendance for event: $eventId")
        return eventWithAttendancesDao.getEventWithAttendances(eventId)
            .map {
                Log.d("DB_DEBUG", "Received ${it.attendees.size} attendees")
                it
            }
    }

    suspend fun deleteAttendenceInAnEvent(eventId: Int, studentId: Int) {
        return eventWithAttendancesDao.deleteAttendanceFromAnEvent(eventId,studentId)
    }

    suspend fun upsetAttendenceInAnEvent(attendence : Attendence){
        return eventWithAttendancesDao.upsertAttendanceInAnEvent(attendence)
    }

    fun searchAttendenceByName(query: String, eventId: Int): Flow<List<Attendence>> {
        return eventWithAttendancesDao.searchEventsbyName(query,eventId)
    }

    fun searchAttendenceByEmail(query: String, eventId: Int): Flow<List<Attendence>> {
        return eventWithAttendancesDao.searchEventsbyEmail(query,eventId)
    }

    fun searchAttendenceByReg(query: String, eventId: Int): Flow<List<Attendence>> {
        return eventWithAttendancesDao.searchEventsbyReg(query,eventId)
    }

    //Fetch an event by eventId
    suspend fun fetchEventByEventId(eventId:Int) : Event?{
        return  eventWithAttendancesDao.fetchEvent(eventId)
    }

    // Add this new function
    suspend fun checkForDuplicates(
        eventId: Int,
        name: String,
        regNo: String,
        email: String,
        phone: String
    ): Boolean {
        return eventWithAttendancesDao.checkForDuplicates(
            eventId = eventId,
            name = name,
            regNo = regNo,
            email = email,
            phone = phone
        )
    }
}
