package com.faradyna.assessment.domain.auth.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "username")
    val username: String? = null,
    @Json(name = "password")
    val password: String? = null,
)