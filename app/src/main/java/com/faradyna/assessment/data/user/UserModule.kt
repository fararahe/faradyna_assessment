package com.faradyna.assessment.data.user

import com.faradyna.assessment.domain.user.UserRepository
import com.faradyna.assessment.utility.consts.AppConst
import com.faradyna.assessment.utility.qualifiers.OkHttpQualifier
import com.faradyna.assessment.utility.qualifiers.UserApiQualifier
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
object UserModule {

    @Provides
    @Singleton
    @UserApiQualifier
    fun provideUserApi(
        @OkHttpQualifier okHttpClient: OkHttpClient
    ): UserApi {
        return Retrofit.Builder()
            .baseUrl(AppConst.API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun providesUserRepository(
        @UserApiQualifier userApi: UserApi,
    ): UserRepository = UserRepositoryImpl(
        userApi = userApi
    )
}