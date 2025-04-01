package com.faradyna.assessment.domain.cart

import com.faradyna.assessment.domain.cart.request.CartRequest

interface CartRepository {

    suspend fun storeCart(carts: CartRequest)

}