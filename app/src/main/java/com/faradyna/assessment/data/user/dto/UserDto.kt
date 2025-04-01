package com.faradyna.assessment.data.user.dto

import com.faradyna.assessment.domain.auth.resp.UserDomain
import com.faradyna.assessment.utility.extensions.orZero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
{
    "address": {
        "geolocation": {
            "lat": "-37.3159",
            "long": "81.1496"
        },
        "city": "kilcoole",
        "street": "new road",
        "number": 7682,
        "zipcode": "12926-3874"
    },
    "id": 1,
    "email": "john@gmail.com",
    "username": "johnd",
    "password": "m38rmF$",
    "name": {
        "firstname": "john",
        "lastname": "doe"
    },
    "phone": "1-570-236-7033",
    "__v": 0
}
*/

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "address")
    val address: AddressDto?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: NameDto?,
    @Json(name = "password")
    val password: String?,
    @Json(name = "phone")
    val phone: String?,
    @Json(name = "username")
    val username: String?,
    @Json(name = "__v")
    val v: Int?
) {
    fun toDomain() = UserDomain(
        address = address?.toDomain(),
        email = email.orEmpty(),
        id = id.orZero(),
        name = name?.toDomain(),
        password = password.orEmpty(),
        phone = phone.orEmpty(),
        username = username.orEmpty(),
        v = v.orZero()
    )
}