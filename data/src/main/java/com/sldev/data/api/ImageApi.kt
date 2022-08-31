package com.sldev.data.api

import com.sldev.data.utils.AUTH_ID
import com.sldev.data.utils.ROUTE_IMAGE_LIST
import com.sldev.data.model.ImageApiModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ImageApi {
    @Headers("Authorization: Client-ID $AUTH_ID")
    @GET(ROUTE_IMAGE_LIST)
    suspend fun getImages(@Query("page") page: Int): List<ImageApiModel>
}