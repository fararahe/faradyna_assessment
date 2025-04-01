package com.faradyna.assessment.core.configs.base.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponseWrapper<T: Any>(
    @Json(name = "error")
    var error: Int = 0,
    @Json(name = "msg")
    var message: String? = null,
    @Json(name = "data")
    var data: T? = null
)
