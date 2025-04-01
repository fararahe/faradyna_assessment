package com.faradyna.assessment.data.auth

import com.faradyna.assessment.core.configs.networking.ApiHandler
import com.faradyna.assessment.domain.auth.AuthRepository
import com.faradyna.assessment.domain.auth.request.LoginRequest
import com.faradyna.assessment.domain.auth.resp.TokenDomain
import com.faradyna.assessment.utility.extensions.toRequestBody
import com.squareup.moshi.Moshi
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    moshi: Moshi
): AuthRepository {

    private val loginAdapter = moshi.adapter(LoginRequest::class.java)

    override suspend fun login(request: LoginRequest): TokenDomain? = ApiHandler.handleApi {
        authApi.postLogin(request.toRequestBody(loginAdapter))
    }?.toDomain()

}