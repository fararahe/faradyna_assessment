package com.faradyna.assessment.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.faradyna.assessment.R
import com.faradyna.assessment.utility.enums.ButtonStyle
import com.faradyna.assessment.utility.extensions.compose.getColors
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    style: ButtonStyle = ButtonStyle.PRIMARY,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = style.getColors(),
        enabled = isEnabled
    ) {
        ButtonContent(
            isLoading = isLoading,
            text = text
        )
    }
}

@Composable
fun ButtonContent(isLoading: Boolean, text: String) {
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (isLoading) {
            Image(
                painter = rememberDrawablePainter(
                    drawable =
                        ContextCompat.getDrawable(context, R.drawable.loading)
                ),
                contentDescription = stringResource(R.string.desc_loading),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewButton() {
    Column {
        CustomButton(
            text = "PRIMARY",
            style = ButtonStyle.PRIMARY,
            isLoading = true,
            onClick = { /* Handle Click */ }
        )

        CustomButton(
            text = "SECONDARY",
            style = ButtonStyle.SECONDARY,
            onClick = { /* Handle Click */ }
        )

        CustomButton(
            text = "TERTIARY",
            style = ButtonStyle.TERTIARY,
            onClick = { /* Handle Click */ }
        )

        CustomButton(
            text = "OUTLINED",
            style = ButtonStyle.OUTLINED,
            onClick = { /* Handle Click */ }
        )
    }
}