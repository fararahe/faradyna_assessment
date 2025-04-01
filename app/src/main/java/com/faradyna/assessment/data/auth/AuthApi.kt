package com.faradyna.assessment.data.auth

import com.faradyna.assessment.data.auth.dto.TokenDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun postLogin(
        @Body request: RequestBody
    ): Response<TokenDto>

}