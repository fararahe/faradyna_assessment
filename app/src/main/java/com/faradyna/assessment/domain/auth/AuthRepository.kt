package com.faradyna.assessment.domain.auth

import com.faradyna.assessment.domain.auth.request.LoginRequest
import com.faradyna.assessment.domain.auth.resp.TokenDomain

interface AuthRepository {

    suspend fun login(request: LoginRequest): TokenDomain?

}