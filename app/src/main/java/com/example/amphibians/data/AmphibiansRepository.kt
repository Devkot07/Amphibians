package com.example.amphibians.data

import com.example.amphibians.model.Amphibians
import com.example.amphibians.network.AmphibiansApiService

interface AmphibiansRepository {

    suspend fun getAmphibiansInfo(): List<Amphibians>
}

class NetworkAmphibiansRepository(
    private val amphibiansApiService: AmphibiansApiService
) : AmphibiansRepository {
    override suspend fun getAmphibiansInfo(): List<Amphibians> {
        return amphibiansApiService.getInfo()
    }
}
