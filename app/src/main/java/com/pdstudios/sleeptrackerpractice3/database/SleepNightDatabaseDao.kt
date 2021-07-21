package com.pdstudios.sleeptrackerpractice3.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SleepNightDatabaseDao {
    @Insert
    fun insert(night: SleepNight)

    @Update
    fun update(night: SleepNight)

    @Query("SELECT * FROM sleep_night_table3 WHERE nightId = :key")
    fun get(key: Long): SleepNight

    @Query("SELECT * FROM sleep_night_table3 ORDER BY nightId DESC LIMIT 1")
    fun getTonight(): SleepNight

    @Query("SELECT * FROM sleep_night_table3 ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>

    @Query("DELETE FROM sleep_night_table3 WHERE nightId = :key")
    fun delete(key: Long)

    @Query("DELETE FROM sleep_night_table3")
    fun clear()
}