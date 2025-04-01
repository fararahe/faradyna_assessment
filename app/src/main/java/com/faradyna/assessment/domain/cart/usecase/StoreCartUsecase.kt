package com.faradyna.assessment.domain.cart.usecase

import com.faradyna.assessment.core.configs.base.BaseSuspendUseCase
import com.faradyna.assessment.core.configs.state.ResultState
import com.faradyna.assessment.domain.cart.CartRepository
import com.faradyna.assessment.domain.cart.request.CartRequest
import com.faradyna.assessment.domain.cart.resp.CartDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoreCartUsecase @Inject constructor(
    private val repository: CartRepository
): BaseSuspendUseCase<StoreCartUsecase.RequestValues, StoreCartUsecase.ResponseValues>() {

    class RequestValues(val request: CartRequest) : BaseSuspendUseCase.RequestValues

    class ResponseValues(val result: ResultState<CartDomain?>) : BaseSuspendUseCase.ResponseValues

    override fun execute(requestValues: RequestValues): Flow<ResponseValues> {
        return flow {
            emit(ResponseValues(ResultState.Loading))
            try {
                repository.storeCart(requestValues.request)
                emit(ResponseValues(ResultState.Success(null)))
            } catch (e: Exception) {
                emit(ResponseValues(ResultState.Error(e)))
            }
        }
    }
}