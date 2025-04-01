package com.faradyna.assessment.data.product

import com.faradyna.assessment.domain.product.ProductRepository
import com.faradyna.assessment.utility.consts.AppConst
import com.faradyna.assessment.utility.qualifiers.OkHttpQualifier
import com.faradyna.assessment.utility.qualifiers.ProductApiQualifier
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
object ProductModule {

    @Provides
    @Singleton
    @ProductApiQualifier
    fun provideProductApi(
        @OkHttpQualifier okHttpClient: OkHttpClient
    ): ProductApi {
        return Retrofit.Builder()
            .baseUrl(AppConst.API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun providesProductRepository(
        @ProductApiQualifier productApi: ProductApi,
    ): ProductRepository = ProductRepositoryImpl(
        productApi = productApi
    )
}