package com.faradyna.assessment.domain.cart.resp

import com.faradyna.assessment.domain.product.resp.ProductDomain

data class CartDomain(
    val id: Int,
    val userId: Int,
    val products: List<ProductDomain>
)