package com.example.securityscanapp.data.repository

import android.util.Log
import com.example.securityscanapp.data.dao.EventDAO
import com.example.securityscanapp.data.entities.Event
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(private val eventDao: EventDAO) {

    /** ───  EVENT OPERATIONS ─────────────────────────── */

    // Insert or Update an Event
    suspend fun upsertEvent(event: Event) {
        eventDao.upsertEvent(event)
    }

    // Delete an Event
    suspend fun deleteEvent(event: Event) {
        Log.d("DeleteEvent", "Repository deleting event: ${event.eventname}")
        eventDao.deleteEvent(event)
    }

    // Get All Events
    fun getAllEvents(): Flow<List<Event>> {
        return eventDao.getAllEvents()
    }

    // Get All Events Sorted by Date
    fun getAllEventsByDate(): Flow<List<Event>> {
        return eventDao.getAllEventsByDate()
    }

    // Get All Events Sorted by Name
    fun getAllEventsByName(): Flow<List<Event>> {
        return eventDao.getallEventsByName()
    }

    // Search Events by Name
    fun searchEvents(query: String): Flow<List<Event>> {
        return eventDao.searchEvents(query)
    }
}
