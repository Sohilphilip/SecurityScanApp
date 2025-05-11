package com.example.securityscanapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityscanapp.data.entities.Event
import com.example.securityscanapp.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventRecordsViewmodel @Inject constructor(
    val eventRepository : EventRepository
) : ViewModel() {
    private val _searchText = MutableStateFlow("") //for searching text
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false) //for displaying process bar
    val isSearching = _isSearching.asStateFlow()

    private val _events = MutableStateFlow(listOf<Event>())
    val events = _events.asStateFlow()

    init{
        fetchAllEvents()
    }

    fun fetchAllEvents() {
        viewModelScope.launch{
            eventRepository.getAllEvents().collect(){
                eventlist ->
                _events.value = eventlist
            }
        }
    }

    fun onSearchQueryChange(query: String){
        _searchText.value = query
        performSearch(query)
    }

    fun performSearch(query: String) {
        viewModelScope.launch {
            _isSearching.value = true

            if (query.isEmpty()) {
                fetchAllEvents() // Reset when query is empty
            } else {
                val filteredEvents = eventRepository.searchEvents(query).first()
                _events.value = filteredEvents
            }

            _isSearching.value = false
        }
    }



    fun insertEvent(eventname : String , eventdate : String , eventtime : String){
        viewModelScope.launch {
            eventRepository.upsertEvent(Event(eventname = eventname, eventdate = eventdate, eventtime = eventtime))
        }
    }

    fun upsertEvent(event : Event){
        viewModelScope.launch {
            eventRepository.upsertEvent(event)
        }
    }

    fun deleteEvent(event : Event){
        viewModelScope.launch {
            Log.d("DeleteEvent", "Deleting event: ${event.eventname}")
            eventRepository.deleteEvent(event)

            // Check if the event list updates after deletion
            val updatedList = eventRepository.getAllEvents().first()
            Log.d("DeleteEvent", "Updated event list: $updatedList")

            _events.value = updatedList
        }
    }

}