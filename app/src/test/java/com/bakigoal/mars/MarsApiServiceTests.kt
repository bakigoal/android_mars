package com.bakigoal.mars

import com.bakigoal.mars.network.MarsApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MarsApiServiceTests : BaseTest() {

    private lateinit var service: MarsApiService

    @Before
    fun setup() {
        val url = mockWebServer.url("/")
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(MarsApiService::class.java)
    }

    @Test
    fun api_service() {
        enqueue("mars.json")
        runBlocking {
            val apiResponse = service.getPropertiesAsync().await()

            assertNotNull(apiResponse)
            assertTrue("The list was empty", apiResponse.isNotEmpty())
            assertEquals("The IDs did not match", "424905", apiResponse[0].id)
            assertEquals("The Types did not match", "rent", apiResponse[0].type)
            assertEquals("The Prices did not match", 1234.0, apiResponse[0].price, 0.0)
        }
    }
}