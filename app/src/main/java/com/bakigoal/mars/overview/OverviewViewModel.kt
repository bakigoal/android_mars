package com.bakigoal.mars.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bakigoal.mars.network.MarsApi
import com.bakigoal.mars.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData(MarsApiStatus.LOADING)
    private val _properties = MutableLiveData<List<MarsProperty>>()
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()

    val status: LiveData<MarsApiStatus>
        get() = _status
    val properties: LiveData<List<MarsProperty>>
        get() = _properties
    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty

    init {
        getMarsRealEstateProperties()
    }

    private fun getMarsRealEstateProperties() {
        coroutineScope.launch {
            val propertiesDeferred = MarsApi.retrofitService.getPropertiesAsync()
            try {
                _status.value = MarsApiStatus.LOADING
                val listResult = propertiesDeferred.await()
                _status.value = MarsApiStatus.DONE
                _properties.value = listResult
            } catch (t: Throwable) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayPropertyDetails(property: MarsProperty) {
        _navigateToSelectedProperty.value = property
    }

    fun navigateToSelectedPropertyComplete() {
        _navigateToSelectedProperty.value = null
    }
}
