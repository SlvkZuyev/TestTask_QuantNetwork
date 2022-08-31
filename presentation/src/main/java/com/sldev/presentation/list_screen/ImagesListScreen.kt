package com.sldev.presentation.list_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sldev.data.model.User
import com.sldev.domain.model.ImageModel
import com.sldev.presentation.full_size_picture_screen.FullSizePictureScreen

object ImagesListScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        ImagesListScreenContent(getViewModel())
    }
}

@Composable
fun ImagesListScreenContent(viewModel: ImagesListViewModel) {
    val images: LazyPagingItems<ImageModel> = viewModel.images.collectAsLazyPagingItems()
    val navigator = LocalNavigator.current
    val pagingLoadStates = images.loadState.source
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = pagingLoadStates) {
        viewModel.handlePagerLoadingStates(pagingLoadStates)
        if (viewModel.shouldShowErrorSnackbar) {
            val result = snackbarHostState.showSnackbar(
                message = "Error on loading images",
                withDismissAction = false,
                actionLabel = "Retry",
                duration = SnackbarDuration.Indefinite
            )
            when (result) {
                SnackbarResult.Dismissed -> {}
                SnackbarResult.ActionPerformed -> {
                    images.retry()
                }
            }
        }
    }

    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        items(images.itemCount) { index ->
            images[index]?.let {
                ImageCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigator?.push(FullSizePictureScreen(it)) },
                    image = it
                )
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        SnackbarHost(hostState = snackbarHostState, Modifier.align(Alignment.BottomCenter))
    }
}