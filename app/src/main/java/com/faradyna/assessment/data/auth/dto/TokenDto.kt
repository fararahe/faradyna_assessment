package com.faradyna.assessment.data.auth.dto

import com.faradyna.assessment.domain.auth.resp.TokenDomain
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenDto(
    @Json(name = "token")
    val token: String? = null,
) {
    fun toDomain(): TokenDomain = TokenDomain(token.orEmpty())
}