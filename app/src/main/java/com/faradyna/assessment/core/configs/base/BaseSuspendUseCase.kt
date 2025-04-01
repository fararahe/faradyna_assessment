package com.faradyna.assessment.core.configs.base

import kotlinx.coroutines.flow.Flow

abstract class BaseSuspendUseCase<REQUEST : BaseSuspendUseCase.RequestValues, RESPONSE : BaseSuspendUseCase.ResponseValues> {

    abstract fun execute(requestValues: REQUEST): Flow<RESPONSE>

    interface RequestValues

    interface ResponseValues

}