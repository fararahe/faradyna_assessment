package com.faradyna.assessment.di

import com.faradyna.assessment.data.room.cart.CartDao
import com.faradyna.assessment.data.room.cart.RoomCartRepositoryImpl
import com.faradyna.assessment.data.room.user.RoomUserRepositoryImpl
import com.faradyna.assessment.data.room.user.UserDao
import com.faradyna.assessment.domain.room.cart.RoomCartRepository
import com.faradyna.assessment.domain.room.user.RoomUserRepository
import com.faradyna.assessment.utility.qualifiers.CartDaoQualifier
import com.faradyna.assessment.utility.qualifiers.UserDaoQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomRepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        @UserDaoQualifier userDao: UserDao
    ): RoomUserRepository = RoomUserRepositoryImpl(userDao)

    @Provides
    @Singleton
    fun provideCartRepository(
        @CartDaoQualifier cartDao: CartDao
    ): RoomCartRepository = RoomCartRepositoryImpl(cartDao)

}