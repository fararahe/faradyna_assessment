package com.faradyna.assessment.data.cart

import com.faradyna.assessment.core.configs.networking.ApiHandler
import com.faradyna.assessment.domain.cart.CartRepository
import com.faradyna.assessment.domain.cart.request.CartRequest
import com.faradyna.assessment.utility.extensions.toRequestBody
import com.squareup.moshi.Moshi
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartApi: CartApi,
    moshi: Moshi
) : CartRepository {

    val cartAdapter = moshi.adapter(CartRequest::class.java)

    override suspend fun storeCart(carts: CartRequest) {
        ApiHandler.handleApi {
            cartApi.storeCarts(carts.toRequestBody(cartAdapter))
        }
    }
}