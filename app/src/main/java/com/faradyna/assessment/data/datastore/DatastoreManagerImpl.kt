package com.faradyna.assessment.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.faradyna.assessment.domain.datastore.DatastoreManager
import com.faradyna.assessment.domain.room.user.UserEntityDomain
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatastoreManagerImpl @Inject constructor(
    private val preference: DataStore<Preferences>,
    moshi: Moshi,
): DatastoreManager {

    private val userAdapter = moshi.adapter(UserEntityDomain::class.java)

    override suspend fun storeUser(user: UserEntityDomain) {
        preference.edit {
            it[userKey] = userAdapter.toJson(user)
        }
    }

    override fun getUser(): Flow<UserEntityDomain?> {
        return preference.data.map {
            it[userKey]?.let { user ->
                userAdapter.fromJson(user)
            }
        }
    }

    override suspend fun clearDataStore() {
        preference.edit { it.clear() }
    }

    companion object {
        private const val USER = "USER"
        private val userKey = stringPreferencesKey(USER)
    }
}