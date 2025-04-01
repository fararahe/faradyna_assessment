package com.faradyna.assessment.core.configs.networking

import com.faradyna.assessment.domain.auth.AuthRepository
import com.faradyna.assessment.domain.datastore.EncryptedPrefManager
import com.faradyna.assessment.utility.qualifiers.EncryptedPrefManagerQualifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @EncryptedPrefManagerQualifier private val pref: EncryptedPrefManager,
    private val authRepository: AuthRepository,
) {

    private val _tokenState = MutableStateFlow<String?>(null)

    private val mutex = Mutex() // Prevent concurrent refresh

    init {
        _tokenState.value = pref.getToken()
    }

    fun logout() {
        _tokenState.value = null
        pref.clearCredentials()
    }
}