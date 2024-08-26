package com.kfadli.fdj.features.ui

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun BadgeFromUrl(
    imageUrl: String,
    contentDescription: String,
) {
    AsyncImage(
        model =
            ImageRequest
                .Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
        placeholder = painterResource(com.kfadli.fdj.domain.R.drawable.baseline_image_24),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(120.dp),
    )
}
