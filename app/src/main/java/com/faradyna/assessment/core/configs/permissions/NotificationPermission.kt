package com.faradyna.assessment.core.configs.permissions

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.faradyna.assessment.R
import com.faradyna.assessment.domain.configs.AlertDialogConfig
import com.faradyna.assessment.ui.components.CustomAlertDialog
import com.faradyna.assessment.utility.extensions.openNotificationSettings
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationPermission(
    onPermissionGranted: (Boolean) -> Unit
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val context = LocalContext.current
        val isDialogVisible = remember { mutableStateOf(false) }
        val permissionRequestAttempted = remember { mutableStateOf(false) }
        val permissionState = rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS
        )

        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(lifecycleOwner) {
            lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                if (!permissionState.status.isGranted && permissionRequestAttempted.value) {
                    isDialogVisible.value = true
                }
            }
        }

        LaunchedEffect(permissionState) {
            if (!permissionState.status.isGranted && !permissionRequestAttempted.value) {
                permissionRequestAttempted.value = true
                permissionState.launchPermissionRequest()
            }
        }

        if (isDialogVisible.value) {
            CustomAlertDialog(
                config = AlertDialogConfig(
                    title = stringResource(R.string.title_notification_permission),
                    message = stringResource(R.string.label_notification_permission_permanently_denied),
                    showDismissButton = false,
                ),
                onConfirm = {
                    context.openNotificationSettings()
                    isDialogVisible.value = false
                },
                onDismiss = {
                    isDialogVisible.value = true
                }
            )
        }

        if (permissionState.status.isGranted) {
            onPermissionGranted(true)
        }
    }
}