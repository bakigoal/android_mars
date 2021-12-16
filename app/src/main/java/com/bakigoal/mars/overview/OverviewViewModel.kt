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
    private val _property = MutableLiveData<MarsProperty>()

    val status: LiveData<String>
        get() = _status
    val property: LiveData<MarsProperty>
        get() = _property

    init {
        getMarsRealEstateProperties()
    }

    private fun getMarsRealEstateProperties() {
        coroutineScope.launch {
            val propertiesDeferred = MarsApi.retrofitService.getPropertiesAsync()
            try {
                val listResult = propertiesDeferred.await()
                if (listResult.isNotEmpty()) {
                    _property.value = listResult[0]
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
