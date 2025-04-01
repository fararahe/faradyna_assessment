package com.faradyna.assessment.data.room.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faradyna.assessment.domain.room.cart.CartEntityDomain

@Entity(
    tableName = "table_cart"
)
data class CartEntity(
    @PrimaryKey
    @ColumnInfo(name = "product_id") val productId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "qty") val qty: Int,
) {
    fun toDomain() = CartEntityDomain(
        productId = productId,
        title = title,
        category = category,
        description = description,
        image = image,
        price = price,
        qty = qty
    )
}