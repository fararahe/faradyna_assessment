package com.faradyna.assessment.domain.product

import com.faradyna.assessment.domain.product.resp.ProductDomain

interface ProductRepository {

    suspend fun fetchProducts(): List<ProductDomain>?

    suspend fun fetchProduct(id: Int): ProductDomain?

}