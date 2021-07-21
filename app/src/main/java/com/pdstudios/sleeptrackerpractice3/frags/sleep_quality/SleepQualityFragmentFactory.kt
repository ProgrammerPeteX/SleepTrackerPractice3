package com.pdstudios.sleeptrackerpractice3.frags.sleep_quality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pdstudios.sleeptrackerpractice3.database.SleepNightDatabaseDao
import java.lang.IllegalArgumentException

class SleepQualityFragmentFactory(
    private val key: Long,
    private val dao: SleepNightDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepQualityViewModel::class.java)) {
            return SleepQualityViewModel(key, dao) as T
        }
        throw IllegalArgumentException("ViewModel not found")
    }

}
