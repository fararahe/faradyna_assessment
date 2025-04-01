package com.faradyna.assessment.data.room.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun addToCart(item: CartEntity): Long

    @Query("SELECT * FROM table_cart")
    fun getCartItems(): Flow<List<CartEntity>?>

    @Query("SELECT * FROM table_cart WHERE product_id = :productId")
    suspend fun getCartItemByProductId(productId: Int): CartEntity?

    @Query("UPDATE table_cart SET qty = :newQty WHERE product_id = :productId")
    suspend fun updateCartItemQty(productId: Int, newQty: Int)

    @Query("DELETE FROM table_cart WHERE product_id = :productId")
    suspend fun removeFromCart(productId: Int)

    @Query("DELETE FROM table_cart")
    suspend fun clearCart()
}