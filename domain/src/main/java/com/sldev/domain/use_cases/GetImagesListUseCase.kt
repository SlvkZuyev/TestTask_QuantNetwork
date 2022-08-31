package com.sldev.domain.use_cases

import androidx.paging.PagingData
import com.sldev.domain.model.ImageModel
import com.sldev.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesListUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    operator fun invoke(): Flow<PagingData<ImageModel>> {
        return imageRepository.getImages()
    }
}