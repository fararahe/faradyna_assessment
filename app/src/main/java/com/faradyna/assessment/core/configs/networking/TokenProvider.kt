package com.faradyna.assessment.core.configs.networking

import com.faradyna.assessment.domain.datastore.EncryptedPrefManager
import com.faradyna.assessment.utility.qualifiers.EncryptedPrefManagerQualifier
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenProvider @Inject constructor(
    @EncryptedPrefManagerQualifier private val pref: EncryptedPrefManager
) {
    private var authToken: String? = null

    fun generateAuthHeader() {
        pref.getToken().let {
            authToken = "Bearer $it"
        }
    }

    fun getToken(): String? = authToken
}