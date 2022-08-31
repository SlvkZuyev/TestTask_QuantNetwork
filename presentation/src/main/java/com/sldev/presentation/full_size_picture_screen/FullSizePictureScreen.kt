package com.sldev.presentation.full_size_picture_screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.Navigator
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.sldev.domain.model.ImageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FullSizePictureScreen(private val image: ImageModel) : AndroidScreen() {
    @Composable
    override fun Content() {
        FullSizePictureScreenContent(image)
    }
}

class FullSizePictureScreenState(
    private val image: ImageModel,
    private val scope: CoroutineScope,
    val snackbarHostState: SnackbarHostState,
    private val context: Context,
) {
    var shouldShowProgressIndicator: Boolean by mutableStateOf(false)
    var retryHash: Int by mutableStateOf(0)
    var imageRequest: ImageRequest by mutableStateOf(createImageRequest())

    fun handleAsyncImageStateChange(state: AsyncImagePainter.State) {
        when (state) {
            AsyncImagePainter.State.Empty -> {}
            is AsyncImagePainter.State.Error -> {
                shouldShowProgressIndicator = false
                showErrorSnackbar()
            }
            is AsyncImagePainter.State.Loading -> {
                shouldShowProgressIndicator = true
            }
            is AsyncImagePainter.State.Success -> {
                shouldShowProgressIndicator = false
            }
        }
    }

    private fun createImageRequest(): ImageRequest {
        return ImageRequest.Builder(context)
            .data(image.urls.large)
            .crossfade(true)
            .setParameter("retry_hash", retryHash, memoryCacheKey = null)
            .build()
    }


    private fun showErrorSnackbar() = scope.launch {
        val result = snackbarHostState.showSnackbar(
            message = "Error on loading image",
            withDismissAction = false,
            actionLabel = "Retry",
            duration = SnackbarDuration.Indefinite
        )

        when (result) {
            SnackbarResult.Dismissed -> {

            }
            SnackbarResult.ActionPerformed -> {
                retryHash++
                imageRequest = createImageRequest()
            }
        }
    }
}

@Composable
fun rememberFullSizePictureScreenState(
    image: ImageModel,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current
): FullSizePictureScreenState = remember(image, snackbarHostState) {
    FullSizePictureScreenState(image, scope, snackbarHostState, context)
}

@Composable
fun FullSizePictureScreenContent(image: ImageModel) {
    val state = rememberFullSizePictureScreenState(image)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.shouldShowProgressIndicator) {
            CircularProgressIndicator()
        }
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = state.imageRequest,
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
            onState = { state.handleAsyncImageStateChange(it) }
        )

        SnackbarHost(
            hostState = state.snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

}