package com.example.securityscanapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

//this is a  one to many reletionship betwen a event and attendence records stored under it.
data class EventAttendenceRelation(
    @Embedded
    val event: Event, //Parent Table
    @Relation(
        parentColumn = "eventid",
        entityColumn = "eventid"
    )
    val attendees: List<Attendence> //List of Attendence for that Event
)
//@Embedded val event: Event → Fetches event details.
//@Relation → Links eventid in Event and Attendence.
//Room will automatically fetch all attendances for an event.