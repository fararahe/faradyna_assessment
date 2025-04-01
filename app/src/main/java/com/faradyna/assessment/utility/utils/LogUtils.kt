package com.faradyna.assessment.utility.utils

import com.faradyna.assessment.utility.enums.LogCategory
import timber.log.Timber

fun logMessage(category: LogCategory = LogCategory.MESSAGE, message: String, throwable: Throwable? = null) {
    val formattedTag = "Faradyna-${category.name}"
    if (throwable != null) {
        Timber.tag(formattedTag).e(throwable, message)
    } else {
        Timber.tag(formattedTag).d(message)
    }
}