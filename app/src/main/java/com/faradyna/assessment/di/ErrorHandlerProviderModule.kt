package com.faradyna.assessment.di

import com.faradyna.assessment.data.provider.ErrorHandlerProviderImpl
import com.faradyna.assessment.domain.provider.ErrorHandlerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ErrorHandlerProviderModule {

    @Provides
    @Singleton
    fun providesErrorHandlerProvider(): ErrorHandlerProvider =
        ErrorHandlerProviderImpl()
}