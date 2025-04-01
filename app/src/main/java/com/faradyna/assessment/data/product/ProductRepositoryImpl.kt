package com.faradyna.assessment.data.product

import com.faradyna.assessment.core.configs.networking.ApiHandler
import com.faradyna.assessment.domain.product.ProductRepository
import com.faradyna.assessment.domain.product.resp.ProductDomain
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
): ProductRepository{

    override suspend fun fetchProducts(): List<ProductDomain>? = ApiHandler.handleApi {
        productApi.fetchProducts()
    }?.map { it.toDomain() }

    override suspend fun fetchProduct(id: Int): ProductDomain? = ApiHandler.handleApi {
        productApi.fetchProduct(id)
    }?.toDomain()

}