package com.example.securityscanapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "eventid")
    val eventid : Int = 0,
    val eventname : String,
    val eventdate : String,
    val eventtime : String
)