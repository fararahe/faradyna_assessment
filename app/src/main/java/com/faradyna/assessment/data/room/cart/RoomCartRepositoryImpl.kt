package com.faradyna.assessment.data.room.cart

import com.faradyna.assessment.domain.room.cart.CartEntityDomain
import com.faradyna.assessment.domain.room.cart.RoomCartRepository
import com.faradyna.assessment.utility.utils.logMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomCartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : RoomCartRepository {

    override suspend fun addToCart(item: CartEntityDomain): Long =
        cartDao.addToCart(
            CartEntity(
                productId = item.productId,
                title = item.title,
                category = item.category,
                description = item.description,
                image = item.image,
                price = item.price,
                qty = item.qty
            )
        )

    override fun getCartItems(): Flow<List<CartEntityDomain>?> =
        cartDao.getCartItems()
            .map { list ->
                list?.map { data ->
                    CartEntityDomain(
                        productId = data.productId,
                        title = data.title,
                        category = data.category,
                        description = data.description,
                        image = data.image,
                        price = data.price,
                        qty = data.qty
                    )
                } ?: emptyList()
            }

    override suspend fun getCartItemByProductId(productId: Int): CartEntityDomain? =
        cartDao.getCartItemByProductId(productId)?.let { data ->
            logMessage(message = "data :$data")
            CartEntityDomain(
                productId = productId,
                title = data.title,
                category = data.category,
                description = data.description,
                image = data.image,
                price = data.price,
                qty = data.qty
            )
        }

    override suspend fun updateCartItemQty(productId: Int, newQty: Int): Int {
        cartDao.updateCartItemQty(productId = productId, newQty = newQty)
        return productId
    }

    override suspend fun removeFromCart(productId: Int) =
        cartDao.removeFromCart(productId)

    override suspend fun clearCart() = cartDao.clearCart()
}