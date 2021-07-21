package com.pdstudios.sleeptrackerpractice3.frags.sleep_tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pdstudios.sleeptrackerpractice3.database.SleepNightDatabaseDao
import java.lang.IllegalArgumentException

class SleepTrackingViewModelFactory(
    private val dao: SleepNightDatabaseDao
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepTrackingViewModel::class.java)) {
            return SleepTrackingViewModel(dao) as T
        }
        throw IllegalArgumentException("ViewModel not found")
    }

}
