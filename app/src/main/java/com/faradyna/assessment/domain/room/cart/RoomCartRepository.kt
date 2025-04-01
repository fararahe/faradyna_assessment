package com.faradyna.assessment.domain.room.cart

import kotlinx.coroutines.flow.Flow

interface RoomCartRepository {

    suspend fun addToCart(item: CartEntityDomain): Long

    fun getCartItems(): Flow<List<CartEntityDomain>?>

    suspend fun getCartItemByProductId(productId: Int): CartEntityDomain?

    suspend fun updateCartItemQty(productId: Int, newQty: Int): Int

    suspend fun removeFromCart(productId: Int)

    suspend fun clearCart()

}