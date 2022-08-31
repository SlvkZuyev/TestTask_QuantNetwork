package com.sldev.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sldev.data.datasource.ImageRemoteDatasource
import com.sldev.data.model.mappers.toImageModel
import com.sldev.domain.model.ImageModel

class ImagesPagingDataSource(
    private val remoteDatasource: ImageRemoteDatasource,
) : PagingSource<Int, ImageModel>() {
    override fun getRefreshKey(state: PagingState<Int, ImageModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageModel> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
            val response = remoteDatasource.getImages(nextPageNumber)
            return LoadResult.Page(
                data = response.map { it.toImageModel() },
                prevKey = if (nextPageNumber == STARTING_PAGE_INDEX) null else nextPageNumber.minus(1),
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}