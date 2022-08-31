package com.sldev.domain.repository

import androidx.paging.PagingData
import com.sldev.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ImageRepository {
    fun getImages(): Flow<PagingData<ImageModel>>
}