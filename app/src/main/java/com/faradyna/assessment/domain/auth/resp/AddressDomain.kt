package com.faradyna.assessment.domain.auth.resp

data class AddressDomain(
    val city: String,
    val geolocation: GeolocationDomain?,
    val number: Int,
    val street: String,
    val zipcode: String
)