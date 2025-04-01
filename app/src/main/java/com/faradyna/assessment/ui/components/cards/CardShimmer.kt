package com.faradyna.assessment.ui.components.cards

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faradyna.assessment.utility.extensions.compose.shimmerOverlay

@Composable
fun CardShimmer(
    modifier: Modifier = Modifier,
) {
    DefaultCard(
        modifier = modifier.shimmerOverlay(),
        content = {}
    )
}

@Preview
@Composable
fun PreviewCardShimmer() {
    CardShimmer(
        modifier = Modifier.size(70.dp, 24.dp)
    )
}