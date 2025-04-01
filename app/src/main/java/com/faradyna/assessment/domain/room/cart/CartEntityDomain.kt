package com.faradyna.assessment.domain.room.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartEntityDomain(
    val productId: Int,
    val title: String,
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    var qty: Int
): Parcelable