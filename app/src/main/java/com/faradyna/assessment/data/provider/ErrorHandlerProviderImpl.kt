package com.faradyna.assessment.data.provider

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.faradyna.assessment.R
import com.faradyna.assessment.core.configs.exception.ServerErrorException
import com.faradyna.assessment.domain.provider.ErrorHandlerProvider
import com.faradyna.assessment.utility.enums.LogCategory
import com.faradyna.assessment.utility.extensions.toastShortExt
import com.faradyna.assessment.utility.utils.logMessage
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorHandlerProviderImpl @Inject constructor(): ErrorHandlerProvider {

    private var toastJob: Job? = null

    override fun generalError(
        context: Context,
        lifecycle: LifecycleOwner,
        throwable: Throwable
    ) {
        logMessage(LogCategory.API, "ERROR API, caused:" , throwable)
        val message = when (throwable) {
            is UnknownHostException, is SocketTimeoutException ->
                context.getString(R.string.label_no_internet_error_message)

            is ServerErrorException ->
                context.getString(R.string.label_server_error_message)

            is JsonDataException ->
                context.getString(R.string.label_parsing_error_message)

            else -> {
                context.getString(R.string.label_unexpected_error_message)
            }
        }

        if(shouldShowToast(throwable)) {
            safeErrorMessage(
                context,
                lifecycle,
                message
            )
        }
    }

    private fun safeErrorMessage(
        context: Context,
        lifecycle: LifecycleOwner,
        message: String,
    ) {
        lifecycle.lifecycleScope.launch {
            toastJob?.cancel()
            toastJob =
                launch {
                    delay(JOB_DELAY)
                    context.toastShortExt(message)
                }
        }
    }

    private fun shouldShowToast(throwable: Throwable): Boolean {
        return when (throwable) {
            is UnknownHostException, is SocketTimeoutException -> false  // UI handles these
            is ServerErrorException, is JsonDataException -> true  // Show toast for backend issues
            else -> true  // Default: toast
        }
    }

    companion object {
        private const val JOB_DELAY = 1000L
    }
}