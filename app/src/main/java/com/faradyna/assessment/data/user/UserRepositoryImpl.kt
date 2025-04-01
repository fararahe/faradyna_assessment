package com.faradyna.assessment.data.user

import com.faradyna.assessment.core.configs.networking.ApiHandler
import com.faradyna.assessment.domain.auth.resp.UserDomain
import com.faradyna.assessment.domain.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun fetchUsers(): List<UserDomain>? = ApiHandler.handleApi {
        userApi.getUsers()
    }?.map { it.toDomain() }

}