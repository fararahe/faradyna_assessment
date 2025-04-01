package com.faradyna.assessment.domain.product.resp

data class ProductDomain(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: RatingDomain?,
    val title: String
)
