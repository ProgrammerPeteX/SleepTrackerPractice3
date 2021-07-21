package com.pdstudios.sleeptrackerpractice3.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_night_table3")
data class SleepNight(
    @PrimaryKey(autoGenerate = true)
    var nightId: Long = 0L,
    @ColumnInfo
    var startTimeMillis: Long = System.currentTimeMillis(),
    @ColumnInfo
    var endTimeMillis: Long = startTimeMillis,
    @ColumnInfo
    var quality: Int = -1
)
