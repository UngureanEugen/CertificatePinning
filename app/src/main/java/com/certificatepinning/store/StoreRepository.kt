package com.certificatepinning.store

import com.certificatepinning.data.LocationItem
import com.certificatepinning.data.StoreResponse
import com.certificatepinning.data.StoreService
import retrofit2.Response
import javax.inject.Inject

class StoreRepository @Inject constructor(private val storeService: StoreService) {
    suspend fun getStores(location: LocationItem, page: Int): Response<StoreResponse> {
        return storeService.getStores(
            longitude = location.longitude,
            latitude = location.latitude,
            page = page
        )
    }
}
