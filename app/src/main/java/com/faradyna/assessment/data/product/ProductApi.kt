package com.faradyna.assessment.data.product

import com.faradyna.assessment.data.product.dto.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("products")
    suspend fun fetchProducts(): Response<List<ProductDto>>

    @GET("products/{id}")
    suspend fun fetchProduct(
        @Path("id") id: Int
    ): Response<ProductDto>

}