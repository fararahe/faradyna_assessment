package com.faradyna.assessment.data.user.dto

import com.faradyna.assessment.domain.auth.resp.GeolocationDomain
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeolocationDto(
    @Json(name = "lat")
    val lat: String?,
    @Json(name = "long")
    val long: String?
) {
    fun toDomain() = GeolocationDomain(
        lat = lat.orEmpty(),
        long = long.orEmpty()
    )
}