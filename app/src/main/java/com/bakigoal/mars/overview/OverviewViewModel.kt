package com.bakigoal.mars.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bakigoal.mars.network.MarsApi
import com.bakigoal.mars.network.MarsProperty
import kotlinx.coroutines.*

class OverviewViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<String>()
    private val _properties = MutableLiveData<List<MarsProperty>>()

    val status: LiveData<String>
        get() = _status
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    init {
        getMarsRealEstateProperties()
    }

    private fun getMarsRealEstateProperties() {
        coroutineScope.launch {
            val propertiesDeferred = MarsApi.retrofitService.getPropertiesAsync()
            try {
                val listResult = propertiesDeferred.await()
                if (listResult.isNotEmpty()) {
                    _properties.value = listResult
                }
            } catch (t: Throwable) {
                _status.value = t.message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
