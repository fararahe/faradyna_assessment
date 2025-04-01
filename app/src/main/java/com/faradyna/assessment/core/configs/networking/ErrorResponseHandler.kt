package com.faradyna.assessment.core.configs.networking

import com.faradyna.assessment.core.configs.exception.BadRequestException
import com.faradyna.assessment.core.configs.exception.ServerErrorException
import com.faradyna.assessment.core.configs.exception.UnauthorizedException
import com.faradyna.assessment.utility.consts.ResponseCode
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import java.lang.reflect.Type

abstract class ErrorResponseHandler {
    fun handle(errorBody: ResponseBody?, responseCode: Int): Exception {
        return fetchError(errorBody, responseCode)
    }

    protected abstract fun handleBadRequestError(responseBody: ResponseBody): BadRequestException

    protected abstract fun handleRespBadRequestError(
        message: String?,
        errorCode: Int
    ): BadRequestException

    protected open fun fetchError(errorBody: ResponseBody?, responseCode: Int): Exception {
        return try {
            val exception = when (responseCode) {
                ResponseCode.BAD_REQUEST, ResponseCode.FORBIDDEN_ERROR,
                ResponseCode.UNPROCESSABLE_CONTENT, ResponseCode.NOT_FOUND,
                    -> { // 400, 403, 422, 404
                    errorBody?.let { error ->
                        handleBadRequestError(error)
                    } ?: Exception()
                }

                ResponseCode.EXPIRED -> UnauthorizedException()
                in 500..599 -> {
                    ServerErrorException()
                }

                else -> Exception()
            }
            exception
        } catch (exception: Exception) {
            exception
        }
    }

    protected fun <T> ResponseBody.errorBodyParser(type: Type): T? {
        // Create a Moshi instance
        val moshi = Moshi.Builder().build()
        // Create a JSON adapter for the type
        val adapter: JsonAdapter<T> = moshi.adapter(type)
        // Convert the ResponseBody to a string and parse it to the desired type
        return this.string().let {
            adapter.fromJson(it)
        }
    }
}