package com.faradyna.assessment.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.faradyna.assessment.core.configs.networking.HeaderInterceptor
import com.faradyna.assessment.core.configs.networking.TokenManager
import com.faradyna.assessment.core.configs.networking.TokenProvider
import com.faradyna.assessment.domain.auth.AuthRepository
import com.faradyna.assessment.domain.datastore.EncryptedPrefManager
import com.faradyna.assessment.utility.qualifiers.EncryptedPrefManagerQualifier
import com.faradyna.assessment.utility.qualifiers.OkHttpQualifier
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideTokenProvider(
        @EncryptedPrefManagerQualifier pref: EncryptedPrefManager
    ) : TokenProvider {
        return TokenProvider(pref)
    }

    @Provides
    @Singleton
    fun provideTokenManager(
        @EncryptedPrefManagerQualifier encryptedPrefManager: EncryptedPrefManager,
        authRepository: AuthRepository
    ) : TokenManager {
        return TokenManager(
            pref = encryptedPrefManager,
            authRepository = authRepository
        )
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(
        tokenProvider: TokenProvider,
    ): HeaderInterceptor {
        return HeaderInterceptor(tokenProvider)
    }

    @Provides
    @Singleton
    fun providesChuckerInterceptor(
        @ApplicationContext context: Context
    ): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .redactHeaders("**")
            .alwaysReadResponseBody(true)
            .build()
    }

    @Provides
    @Singleton
    @OkHttpQualifier
    fun providesOkHttpClient(
        headerInterceptor: HeaderInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(chuckerInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }
}