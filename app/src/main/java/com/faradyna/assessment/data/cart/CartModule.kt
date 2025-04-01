package com.faradyna.assessment.data.cart

import com.faradyna.assessment.domain.cart.CartRepository
import com.faradyna.assessment.utility.consts.AppConst
import com.faradyna.assessment.utility.qualifiers.CartApiQualifier
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
object CartModule {

    @Provides
    @Singleton
    @CartApiQualifier
    fun provideCartApi(
        @OkHttpQualifier okHttpClient: OkHttpClient
    ): CartApi {
        return Retrofit.Builder()
            .baseUrl(AppConst.API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CartApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        @CartApiQualifier cartApi: CartApi,
        moshi: Moshi
    ): CartRepository = CartRepositoryImpl (
        moshi = moshi,
        cartApi = cartApi
    )
}