package com.certificatepinning.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreService {

    @GET("stores")
    suspend fun getStores(
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double,
        @Query("radius") radius: Int = 1000,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 10,
        @Query("clientApplicationKey") appKey: String = "testApplication"
    ): Response<StoreResponse>
}
