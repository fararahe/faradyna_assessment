package com.faradyna.assessment.data.auth

import com.faradyna.assessment.domain.auth.AuthRepository
import com.faradyna.assessment.utility.consts.AppConst
import com.faradyna.assessment.utility.qualifiers.AuthApiQualifier
import com.faradyna.assessment.utility.qualifiers.OkHttpQualifier
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    @AuthApiQualifier
    fun provideAuthApi(
        @OkHttpQualifier okHttpClient: OkHttpClient
    ): AuthApi {
        return Retrofit.Builder()
            .baseUrl(AppConst.API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(
        @AuthApiQualifier authApi: AuthApi,
        moshi: Moshi
    ): AuthRepository = AuthRepositoryImpl(
        moshi = moshi,
        authApi = authApi
    )

}