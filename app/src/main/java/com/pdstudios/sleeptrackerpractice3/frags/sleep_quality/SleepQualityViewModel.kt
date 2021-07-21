package com.pdstudios.sleeptrackerpractice3.frags.sleep_quality

import android.provider.SyncStateContract.Helpers.update
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdstudios.sleeptrackerpractice3.database.SleepNight
import com.pdstudios.sleeptrackerpractice3.database.SleepNightDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SleepQualityViewModel(
    private val key: Long,
    private val dao: SleepNightDatabaseDao
): ViewModel() {

    private val _navigateToTracking = MutableLiveData<Boolean?>()
    val navigateToTracking: LiveData<Boolean?>
        get() = _navigateToTracking

    init {
        _navigateToTracking.value = null
    }

    fun onSetQuality(quality: Int) {
        viewModelScope.launch {
            val night = getNight(key)
            night.quality = quality
            update(night)
            _navigateToTracking.value = true
        }
    }

    private suspend fun getNight(key: Long): SleepNight {
        return withContext(Dispatchers.IO) {
            dao.get(key)
        }
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            dao.update(night)
        }
    }
}
