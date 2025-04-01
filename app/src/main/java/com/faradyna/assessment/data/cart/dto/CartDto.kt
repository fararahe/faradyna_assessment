package com.faradyna.assessment.data.cart.dto

import com.faradyna.assessment.data.product.dto.ProductDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
{
    "id": 0,
    "userId": 0,
    "products": [
        {
            "id": 0,
            "title": "string",
            "price": 0.1,
            "description": "string",
            "category": "string",
            "image": "http://example.com"
        }
    ]
}
 */
@JsonClass(generateAdapter = true)
data class CartDto(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "userId")
    val userId: Int?,
    @Json(name = "products")
    val products: List<ProductDto?>?,
)