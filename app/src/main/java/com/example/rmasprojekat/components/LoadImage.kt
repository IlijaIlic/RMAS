package com.example.rmasprojekat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun LoadImage(url: String) {
    val painter = rememberAsyncImagePainter(url)
    Image(
        painter = painter, contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
    )
}