package com.faradyna.assessment.data.product.dto

import com.faradyna.assessment.domain.product.resp.RatingDomain
import com.faradyna.assessment.utility.extensions.orZero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rating(
    @Json(name = "count")
    val count: Int?,
    @Json(name = "rate")
    val rate: Double?
) {
    fun toDomain() = RatingDomain(
        count = count.orZero(),
        rate = rate.orZero()
    )
}