package com.faradyna.assessment.data.product.dto

import com.faradyna.assessment.domain.product.resp.ProductDomain
import com.faradyna.assessment.utility.extensions.orZero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
{
    "id": 1,
    "title": "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
    "price": 109.95,
    "description": "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
    "category": "men's clothing",
    "image": "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
    "rating": {
        "rate": 3.9,
        "count": 120
    }
}
*/

@JsonClass(generateAdapter = true)
data class ProductDto(
    @Json(name = "category")
    val category: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "price")
    val price: Double?,
    @Json(name = "rating")
    val rating: Rating?,
    @Json(name = "title")
    val title: String?
) {
    fun toDomain() = ProductDomain(
        category = category.orEmpty(),
        description = description.orEmpty(),
        id = id.orZero(),
        image = image.orEmpty(),
        price = price.orZero(),
        rating = rating?.toDomain(),
        title = title.orEmpty()
    )
}