package com.faradyna.assessment.domain.datastore

import com.faradyna.assessment.domain.room.user.UserEntityDomain
import kotlinx.coroutines.flow.Flow

interface DatastoreManager {

    suspend fun storeUser(user: UserEntityDomain)

    fun getUser(): Flow<UserEntityDomain?>

    suspend fun clearDataStore()

}