package com.sldev.presentation.list_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sldev.domain.model.ImageModel
import com.sldev.domain.model.Urls

@Composable
fun ImageCard(modifier: Modifier, image: ImageModel) {
    Surface(
        modifier = modifier,
        tonalElevation = 5.dp,
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.urls.small)
                    .crossfade(true)
                    .build(), contentDescription = null,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .aspectRatio(2.7f)
                    .alpha(0.8f),
                color = Color.Black
            ) {
                Column {
                    var descriptionText = "Description: " + (image.description ?: "empty")
                    if (descriptionText.length > 40) {
                        descriptionText = descriptionText.take(40) + "..."
                    }
                    Text(
                        text = descriptionText,
                        color = Color.White,
                    )
                    Text(
                        text = "Author: " + image.author,
                        color = Color.White
                    )
                }
            }
        }

    }
}
