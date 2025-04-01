package com.faradyna.assessment.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faradyna.assessment.R
import com.faradyna.assessment.domain.configs.AlertDialogConfig
import com.faradyna.assessment.utility.enums.ButtonStyle

@Composable
fun CustomAlertDialog(
    config: AlertDialogConfig,
    confirmText: String = stringResource(R.string.label_setting),
    dismissText: String = stringResource(R.string.label_later),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit = {},
) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        title = { TitleSection(config.title) },
        text = {
            config.message?.let {
                MessageSection(it)
            }
        },
        confirmButton = {
            ConfirmSection(
                dismissText = dismissText,
                confirmText = confirmText,
                showDismissButton = config.showDismissButton,
                onDismiss = onDismiss,
                onConfirm = onConfirm,
                isError = config.isError,
            )
        }
    )
}

@Composable
fun ConfirmSection(
    dismissText: String,
    confirmText: String,
    showDismissButton: Boolean,
    isError: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End)
    ) {
        if (showDismissButton) {
            CustomButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                text = dismissText,
                onClick = onDismiss,
                style = ButtonStyle.OUTLINED,
            )
        }
        CustomButton(
            text = confirmText,
            onClick = {
                onConfirm()
            },
            isEnabled = !isError,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
    }
}

@Composable
fun TitleSection(title: String) {
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun MessageSection(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        textAlign = TextAlign.Center
    )
}
