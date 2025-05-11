package com.example.securityscanapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.securityscanapp.data.entities.Attendence
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendenceDAO {

    //Insert new event or update existing attendence
    @Upsert
    suspend fun upsertAttendence(attendance: Attendence)

    //Delete an attendence
    @Delete
    suspend fun deleteAttendence(attendance: Attendence)

    @Query("select * from attendence") //fetches all attendence
    fun getAllAttendence(): Flow<List<Attendence>>

    @Query("SELECT * FROM attendence order by studentregno desc") //fetches all attendence and order by regno desc
    fun getAllAttendenceByRegistrationNo() : Flow<List<Attendence>>

    @Query("select * from attendence order by studentname desc") //fetches all attendence and order by name desc
    fun getAlllAttendenceByName() : Flow<List<Attendence>>

    @Query("select * from attendence order by studentemail desc") //fetches all attendence and order by email desc
    fun getAllAttendenceByEmail() : Flow<List<Attendence>>

    @Query("SELECT * FROM attendence WHERE studentemail LIKE '%' || :query || '%'") //search an attendence by email
    fun searchAttendenceByEmail(query: String): Flow<List<Attendence>>

    @Query("SELECT * FROM attendence WHERE studentregno LIKE '%' || :query || '%'") //search an attendence by reg n
    fun searchAttendenceByRegNo(query: String): Flow<List<Attendence>>

    @Query("SELECT * FROM attendence WHERE studentname LIKE '%' || :query || '%'") //search an attendence by name
    fun searchAttendenceByName(query: String): Flow<List<Attendence>>
}