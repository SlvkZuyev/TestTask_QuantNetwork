package com.sldev.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sldev.data.datasource.ImageRemoteDatasource
import com.sldev.data.paging.ImagesPagingDataSource
import com.sldev.domain.model.ImageModel
import com.sldev.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(private val remoteDatasource: ImageRemoteDatasource) :
    ImageRepository {

    override fun getImages(): Flow<PagingData<ImageModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { ImagesPagingDataSource(remoteDatasource) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}