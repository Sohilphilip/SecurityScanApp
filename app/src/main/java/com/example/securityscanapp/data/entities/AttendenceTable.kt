package com.example.securityscanapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "attendence",
    foreignKeys = [
    ForeignKey(
        entity = Event::class, //Table containing the foreign key
        parentColumns = ["eventid"], // Links to `eventid` in Event
        childColumns = ["eventid"], // Used in Attendance table
        onDelete = ForeignKey.CASCADE //if event is deleted then all attendance associated with it is deleted as well
    )],
    indices = [Index(value = ["eventid"])] //Index improves query speed
    )
data class Attendence(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "studentid")
    val studentid : Int = 0,
    val eventid :Int, // Foreign Key
    val studentname : String,
    val studentregno : String,
    val studentemail : String,
    val studentphone : String
)

//Each Attendence has an eventid → Links to Event (Foreign Key).
//CASCADE: If an Event is deleted, all its Attendence records are also deleted.