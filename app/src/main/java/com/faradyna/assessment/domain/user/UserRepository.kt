package com.faradyna.assessment.domain.user

import com.faradyna.assessment.domain.auth.resp.UserDomain

interface UserRepository {

    suspend fun fetchUsers(): List<UserDomain>?

}