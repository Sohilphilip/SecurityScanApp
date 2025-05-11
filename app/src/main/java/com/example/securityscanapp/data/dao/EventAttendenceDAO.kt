package com.example.securityscanapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.securityscanapp.data.entities.Attendence
import com.example.securityscanapp.data.entities.Event
import com.example.securityscanapp.data.entities.EventAttendenceRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface EventWithAttendancesDao {

    // Fetch an event with all attendance records (One-to-Many relation)
    @Transaction
    @Query("SELECT * FROM events WHERE eventid = :eventId")
    fun getEventWithAttendances(eventId: Int): Flow<EventAttendenceRelation>

    //Delete a specific attendence record from an event
    @Query("DELETE FROM attendence WHERE eventid = :eventId AND studentid = :attendanceId")
    suspend fun deleteAttendanceFromAnEvent(eventId: Int, attendanceId: Int)

    @Upsert
    suspend fun upsertAttendanceInAnEvent(attendence: Attendence)

    @Query("Select * from attendence where eventid = :eventId and studentname LIKE '%' || :query || '%'")
    fun searchEventsbyName(query: String, eventId: Int): Flow<List<Attendence>>

    @Query("Select * from attendence where eventid = :eventId and studentregno LIKE '%' || :query || '%'")
    fun searchEventsbyReg(query: String, eventId: Int): Flow<List<Attendence>>

    @Query("Select * from attendence where eventid = :eventId and studentemail LIKE '%' || :query || '%'")
    fun searchEventsbyEmail(query: String, eventId: Int): Flow<List<Attendence>>

    @Query("SELECT * FROM events WHERE eventid = :eventId LIMIT 1") //search an event by eventid.
    suspend fun fetchEvent(eventId : Int) : Event?

    @Query("""
        SELECT COUNT(*) FROM attendence 
        WHERE eventid = :eventId 
        AND (studentname = :name 
        OR studentregno = :regNo 
        OR studentemail = :email 
        OR studentphone = :phone)
    """)
    suspend fun checkForDuplicates(
        eventId: Int,
        name: String,
        regNo: String,
        email: String,
        phone: String
    ): Boolean

}

