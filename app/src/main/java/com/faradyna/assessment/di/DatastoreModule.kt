package com.faradyna.assessment.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.faradyna.assessment.data.datastore.DatastoreManagerImpl
import com.faradyna.assessment.domain.datastore.DatastoreManager
import com.faradyna.assessment.utility.qualifiers.DataStoreManagerQualifier
import com.faradyna.assessment.utility.qualifiers.DataStoreQualifier
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    private const val SESSION_NAME = "Assessment"
    private val Context.sessionPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        SESSION_NAME
    )

    @Provides
    @Singleton
    @DataStoreQualifier
    fun providesSessionPrefDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.sessionPreferencesDataStore

    @Provides
    @Singleton
    @DataStoreManagerQualifier
    fun providesSessionPrefDataStoreManager(
        @DataStoreQualifier preferences: DataStore<Preferences>,
        moshi: Moshi,
    ): DatastoreManager = DatastoreManagerImpl(preferences, moshi)

}