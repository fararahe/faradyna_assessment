package com.faradyna.assessment.domain.user.usecase

import com.faradyna.assessment.core.configs.base.BaseSuspendUseCase
import com.faradyna.assessment.core.configs.state.ResultState
import com.faradyna.assessment.domain.auth.resp.UserDomain
import com.faradyna.assessment.domain.auth.usecase.LoginUseCase.ResponseValues
import com.faradyna.assessment.domain.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchUsersUsecase @Inject constructor(
    private val repository: UserRepository
) : BaseSuspendUseCase<FetchUsersUsecase.RequestValues, FetchUsersUsecase.ResponseValues>() {

    class RequestValues : BaseSuspendUseCase.RequestValues

    class ResponseValues(val result: ResultState<List<UserDomain>?>) :
        BaseSuspendUseCase.ResponseValues

    override fun execute(requestValues: RequestValues): Flow<ResponseValues> {
        return flow {
            emit(ResponseValues(ResultState.Loading))
            try {
                val data = repository.fetchUsers()
                emit(
                    ResponseValues(
                        ResultState.Success(data)
                    )
                )
            } catch (e: Exception) {
                emit(
                    ResponseValues(
                        ResultState.Error(e)
                    )
                )
            }
        }
    }
}