package com.faradyna.assessment.domain.configs

data class AlertDialogConfig(
    val title: String,
    val message: String? = null,
    val isError: Boolean = false,
    val showDismissButton: Boolean = true,
)