package com.pdstudios.sleeptrackerpractice3.frags.sleep_tracking

import android.util.Log
import androidx.lifecycle.*
import com.pdstudios.sleeptrackerpractice3.database.SleepNight
import com.pdstudios.sleeptrackerpractice3.database.SleepNightDatabaseDao
import com.pdstudios.sleeptrackerpractice3.formatNights
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SleepTrackingViewModel(
    private val dao: SleepNightDatabaseDao
) : ViewModel() {

    private val tonight = MutableLiveData<SleepNight>()
    private val nights = dao.getAllNights()

    var deleteKey = MutableLiveData<Long>()
    private var _deleteAction = MutableLiveData<Boolean?>()
    val deleteAction: LiveData<Boolean?>
        get() = _deleteAction

    val nightsString: LiveData<String> = Transformations.map(nights) { nights ->
        formatNights(nights)
    }

    private val _navigateToQuality = MutableLiveData<SleepNight?>()
    val navigateToQuality: LiveData<SleepNight?>
        get() = _navigateToQuality

    init {
        tonight.value = null
        _navigateToQuality.value = null
        _deleteAction.value = null
    }

    val enableStart = Transformations.map(tonight) { it == null }

    val enableStop = Transformations.map(tonight) { it != null }

    val enableClear = Transformations.map(nights) { it.isNotEmpty() }

    fun onStartTracking() {
        viewModelScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            Log.i("error", "newNight = $newNight")
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insert(newNight: SleepNight) {
        withContext(Dispatchers.IO) {
            dao.insert(newNight)
        }
    }

    private suspend fun getTonightFromDatabase() : SleepNight {
        return withContext(Dispatchers.IO) {
            dao.getTonight()
        }
    }

    fun onStopTracking() {
        viewModelScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMillis = System.currentTimeMillis()
            update(oldNight)
            _navigateToQuality.value = oldNight
            tonight.value = null
        }
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            dao.update(night)
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            _deleteAction.value = true
            delete(deleteKey.value ?: -1L)
            deleteKey.value = null
        }
    }

    private suspend fun delete(key: Long) {
        withContext(Dispatchers.IO) {
            dao.delete(key)
        }
    }
    fun onClear() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dao.clear()
            }
        }
    }

}
