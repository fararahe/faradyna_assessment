package com.faradyna.assessment.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.faradyna.assessment.data.datastore.EncryptedPrefManagerImpl
import com.faradyna.assessment.domain.datastore.EncryptedPrefManager
import com.faradyna.assessment.utility.qualifiers.EncryptedPrefManagerQualifier
import com.faradyna.assessment.utility.qualifiers.EncryptedPrefQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EncryptedPrefModule {

    private const val ENCRYPTED_SESSION_NAME = "EncryptedAssessment"
    private val Context.encryptedSessionPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        ENCRYPTED_SESSION_NAME,
    )

    @Provides
    @Singleton
    fun providesMasterKey(): String {
        return MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    }

    @Provides
    @Singleton
    @EncryptedPrefQualifier
    fun providesEncryptedPref(
        @ApplicationContext context: Context,
        masterKey: String
    ): SharedPreferences = EncryptedSharedPreferences.create(
        ENCRYPTED_SESSION_NAME,
        masterKey,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    @Provides
    @Singleton
    @EncryptedPrefManagerQualifier
    fun providesEncryptedPrefManager(
        @EncryptedPrefQualifier sharedPreferences: SharedPreferences,
    ): EncryptedPrefManager = EncryptedPrefManagerImpl(sharedPreferences)

}