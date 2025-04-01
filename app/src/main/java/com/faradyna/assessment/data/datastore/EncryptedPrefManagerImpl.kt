package com.faradyna.assessment.data.datastore

import android.content.SharedPreferences
import androidx.core.content.edit
import com.faradyna.assessment.domain.datastore.EncryptedPrefManager
import javax.inject.Inject

class EncryptedPrefManagerImpl @Inject constructor(
    private val preference: SharedPreferences
): EncryptedPrefManager {

    override fun saveToken(token: String) {
        preference.edit {
            putString(AT_KEY, token)
        }
    }

    override fun getToken(): String? = preference.getString(AT_KEY, null)

    override fun clearCredentials() {
        preference.edit{ clear() }
    }

    companion object {
        private const val AT_KEY = "AT_KEY"
    }
}