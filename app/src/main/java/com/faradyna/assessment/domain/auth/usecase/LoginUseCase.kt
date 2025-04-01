package com.faradyna.assessment.domain.auth.usecase

import com.faradyna.assessment.core.configs.base.BaseSuspendUseCase
import com.faradyna.assessment.core.configs.state.ResultState
import com.faradyna.assessment.domain.auth.AuthRepository
import com.faradyna.assessment.domain.auth.request.LoginRequest
import com.faradyna.assessment.domain.auth.resp.TokenDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) : BaseSuspendUseCase<LoginUseCase.RequestValues, LoginUseCase.ResponseValues>() {

    class RequestValues(val request: LoginRequest) : BaseSuspendUseCase.RequestValues

    class ResponseValues(val result: ResultState<TokenDomain?>) : BaseSuspendUseCase.ResponseValues

    override fun execute(requestValues: RequestValues): Flow<ResponseValues> {
        return flow {
            emit(ResponseValues(ResultState.Loading))
            try {
                val data = repository.login(requestValues.request)
                emit(ResponseValues(ResultState.Success(data)))
            } catch (e: Exception) {
                emit(ResponseValues(ResultState.Error(e)))
            }
        }
    }
}
