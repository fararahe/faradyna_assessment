package com.faradyna.assessment.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.faradyna.assessment.R

@Composable
fun CustomScaffold(
    backgroundResId: Int = R.drawable.background,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = backgroundResId),
                contentDescription = null,
                contentScale = ContentScale.Crop, // Makes sure image covers the screen
                modifier = Modifier.fillMaxSize()
            )
            content(innerPadding) // Place your actual content on top of the background
        }
    }
}
