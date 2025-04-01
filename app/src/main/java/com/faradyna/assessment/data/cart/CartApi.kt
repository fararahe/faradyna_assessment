package com.faradyna.assessment.data.cart

import com.faradyna.assessment.data.cart.dto.CartDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CartApi {

    @POST("carts")
    suspend fun storeCarts(
        @Body request: RequestBody
    ): Response<CartDto>

}