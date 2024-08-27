package com.example.amphibians.network

import com.example.amphibians.model.Amphibians
import retrofit2.http.GET

interface AmphibiansApiService {

    @GET("amphibians")
    suspend fun getInfo(): List<Amphibians>


}