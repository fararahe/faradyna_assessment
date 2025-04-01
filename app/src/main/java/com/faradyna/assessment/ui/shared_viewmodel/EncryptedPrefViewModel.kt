package com.faradyna.assessment.ui.shared_viewmodel

import androidx.lifecycle.ViewModel
import com.faradyna.assessment.domain.datastore.EncryptedPrefManager
import com.faradyna.assessment.utility.qualifiers.EncryptedPrefManagerQualifier
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EncryptedPrefViewModel @Inject constructor(
    @EncryptedPrefManagerQualifier private val encryptedPrefManager: EncryptedPrefManager,
) : ViewModel() {

    fun saveToken(token: String) {
        encryptedPrefManager.saveToken(token)
    }

    fun clearCredentials() {
        encryptedPrefManager.clearCredentials()
    }

}