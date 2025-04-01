package com.faradyna.assessment.domain.datastore

interface EncryptedPrefManager {

    fun saveToken(token: String)

    fun getToken(): String?

    fun clearCredentials()

}