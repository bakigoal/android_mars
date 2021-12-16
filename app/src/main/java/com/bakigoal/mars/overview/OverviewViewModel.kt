package com.bakigoal.mars.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bakigoal.mars.network.MarsApi
import com.bakigoal.mars.network.MarsProperty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OverviewViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    init {
        getMarsRealEstateProperties()
    }

    private fun getMarsRealEstateProperties() {
        MarsApi.retrofitService.getProperties().enqueue(object : Callback<List<MarsProperty>> {

            override fun onResponse(c: Call<List<MarsProperty>>, r: Response<List<MarsProperty>>) {
                _response.value = "Success: ${r.body()?.size} retrieved"
            }

            override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
                _response.value = t.message
            }
        })
    }
}
