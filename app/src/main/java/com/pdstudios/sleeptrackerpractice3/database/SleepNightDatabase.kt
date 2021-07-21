package com.pdstudios.sleeptrackerpractice3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepNightDatabase: RoomDatabase() {

    abstract val sleepNightDatabaseDao: SleepNightDatabaseDao

    companion object {

        private var INSTANCE: SleepNightDatabase? = null

        fun getInstance(context: Context): SleepNightDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SleepNightDatabase::class.java,
                        "sleep_night_database3")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}