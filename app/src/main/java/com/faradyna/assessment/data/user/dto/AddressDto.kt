package com.faradyna.assessment.data.user.dto

import com.faradyna.assessment.domain.auth.resp.AddressDomain
import com.faradyna.assessment.utility.extensions.orZero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressDto(
    @Json(name = "city")
    val city: String?,
    @Json(name = "geolocation")
    val geolocation: GeolocationDto?,
    @Json(name = "number")
    val number: Int?,
    @Json(name = "street")
    val street: String?,
    @Json(name = "zipcode")
    val zipcode: String?
) {
    fun toDomain() = AddressDomain(
        city = city.orEmpty(),
        geolocation = geolocation?.toDomain(),
        number = number.orZero(),
        street = street.orEmpty(),
        zipcode = zipcode.orEmpty()
    )
}