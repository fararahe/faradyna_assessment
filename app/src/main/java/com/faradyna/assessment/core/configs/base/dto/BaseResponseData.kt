package com.faradyna.assessment.core.configs.base.dto

data class BaseResponseData<T> (
    var error: Int = 0,
    var message: String? = null,
    var data: T? = null
)