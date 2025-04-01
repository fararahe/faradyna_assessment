package com.faradyna.assessment.domain.provider

import android.content.Context
import androidx.lifecycle.LifecycleOwner

interface ErrorHandlerProvider {
    fun generalError(context: Context, lifecycle: LifecycleOwner, throwable: Throwable)
}