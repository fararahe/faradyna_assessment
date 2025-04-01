package com.faradyna.assessment.domain.product.usecase

import com.faradyna.assessment.core.configs.base.BaseSuspendUseCase
import com.faradyna.assessment.core.configs.state.ResultState
import com.faradyna.assessment.domain.product.ProductRepository
import com.faradyna.assessment.domain.product.resp.ProductDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) : BaseSuspendUseCase<FetchProductsUseCase.RequestValues, FetchProductsUseCase.ResponseValues>() {

    class RequestValues : BaseSuspendUseCase.RequestValues

    class ResponseValues(val result: ResultState<List<ProductDomain>?>) : BaseSuspendUseCase.ResponseValues

    override fun execute(requestValues: RequestValues): Flow<ResponseValues> {
        return flow {
            emit(ResponseValues(ResultState.Loading))
            try {
                val data = repository.fetchProducts()
                emit(ResponseValues(ResultState.Success(data)))
            } catch (e: Exception) {
                emit(ResponseValues(ResultState.Error(e)))
            }
        }
    }
}