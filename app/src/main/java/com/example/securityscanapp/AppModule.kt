package com.example.securityscanapp


import android.content.Context
import androidx.room.Room
import com.example.securityscanapp.data.dao.AttendenceDAO
import com.example.securityscanapp.data.dao.EventDAO
import com.example.securityscanapp.data.dao.EventWithAttendancesDao
import com.example.securityscanapp.data.database.AttendanceDatabase
import com.example.securityscanapp.data.repository.EventRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AttendanceDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AttendanceDatabase::class.java,
            "attendance_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideAttendanceDao(database: AttendanceDatabase): AttendenceDAO {
        return database.attendanceDao()
    }

    @Provides
    fun provideEventDao(database: AttendanceDatabase): EventDAO {
        return database.eventDao()
    }

    @Provides
    fun provideEventWithAttendancesDao(database: AttendanceDatabase): EventWithAttendancesDao {
        return database.eventAttendenceDao()
    }

    @Provides
    @Singleton
    fun provideEventRepository(eventDao: EventDAO): EventRepository {
        return EventRepository(eventDao)
    }
}

