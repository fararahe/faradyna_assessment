package com.faradyna.assessment.data.room.user

import com.faradyna.assessment.domain.auth.resp.UserDomain
import com.faradyna.assessment.domain.room.user.UserEntityDomain
import com.faradyna.assessment.domain.room.user.RoomUserRepository
import javax.inject.Inject

class RoomUserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
): RoomUserRepository {

    override suspend fun storeUsers(users: List<UserDomain>) {
        userDao.insertUsers(users = users.map {
            UserEntity(
                id = it.id,
                username = it.username,
                email = it.email,
                firstname = it.name?.firstname.orEmpty(),
                lastname = it.name?.lastname.orEmpty(),
                phone = it.phone
            )
        })
    }

    override suspend fun getUsersCount(): Int {
        return userDao.getUsersCount()
    }

    override suspend fun getUserByUsername(username: String): UserEntityDomain? {
        return userDao.getUserByUsername(username)?.toDomain()
    }
}