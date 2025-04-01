package com.faradyna.assessment.domain.room.user

import com.faradyna.assessment.domain.auth.resp.UserDomain

interface RoomUserRepository {

    suspend fun storeUsers(users: List<UserDomain>)

    suspend fun getUsersCount(): Int

    suspend fun getUserByUsername(username: String): UserEntityDomain?

}