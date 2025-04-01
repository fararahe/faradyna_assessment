package com.faradyna.assessment.data.user

import com.faradyna.assessment.data.user.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {

    @GET("users")
    suspend fun getUsers(): Response<List<UserDto>>

}