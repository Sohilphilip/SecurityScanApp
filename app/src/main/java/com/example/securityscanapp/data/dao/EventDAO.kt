package com.example.securityscanapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.securityscanapp.data.entities.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDAO {

    //Insert new event or update existing element
    @Upsert
    suspend fun upsertEvent(event: Event)

    //Delete an event
    @Delete
    suspend fun deleteEvent(event : Event)

    @Query("select * from events") //fetches all events
    fun getAllEvents(): Flow<List<Event>>

    @Query("SELECT * FROM events order by eventdate desc") //fetches all events and order by eventdate desc
    fun getAllEventsByDate() : Flow<List<Event>>

    @Query("select * from events order by eventname desc") //fetches all events and order by eventname desc
    fun getallEventsByName() : Flow<List<Event>>

    @Query("SELECT * FROM events WHERE eventname LIKE '%' || :query || '%'") //search an event by eventname.
    fun searchEvents(query: String): Flow<List<Event>>

}