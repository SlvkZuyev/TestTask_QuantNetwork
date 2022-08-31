package com.sldev.presentation.list_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.cachedIn
import androidx.paging.map
import com.sldev.domain.use_cases.GetImagesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesListViewModel @Inject constructor(
    private val getImagesListUseCase: GetImagesListUseCase
) : ViewModel() {
    val images = getImagesListUseCase()
        .map { pagingData ->
            pagingData
        }.cachedIn(viewModelScope)
    var shouldShowErrorSnackbar: Boolean by mutableStateOf(false)

    fun handlePagerLoadingStates(states: LoadStates) {
        when (states.refresh) {
            is LoadState.Error -> {
                shouldShowErrorSnackbar = true
            }
            LoadState.Loading -> {
                shouldShowErrorSnackbar = false
            }
            is LoadState.NotLoading -> {}
        }

        when (states.append) {
            is LoadState.Error -> {
                shouldShowErrorSnackbar = true
            }
            LoadState.Loading -> {
                shouldShowErrorSnackbar = false
            }
            is LoadState.NotLoading -> {}
        }
    }
}