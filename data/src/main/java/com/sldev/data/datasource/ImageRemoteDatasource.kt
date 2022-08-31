package com.sldev.data.datasource

import com.sldev.data.api.ImageApi
import com.sldev.data.model.ImageApiModel
import javax.inject.Inject

class ImageRemoteDatasource @Inject constructor(private val api: ImageApi) {
    suspend fun getImages(page: Int = 1): List<ImageApiModel> {
        return api.getImages(page)
    }
}