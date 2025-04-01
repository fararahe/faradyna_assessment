package com.faradyna.assessment.data.user.dto

import com.faradyna.assessment.domain.auth.resp.NameDomain
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NameDto(
    @Json(name = "firstname")
    val firstname: String?,
    @Json(name = "lastname")
    val lastname: String?
) {
    fun toDomain() = NameDomain(
        firstname = firstname.orEmpty(),
        lastname = lastname.orEmpty()
    )
}