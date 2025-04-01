package com.faradyna.assessment.di

import android.content.Context
import androidx.room.Room
import com.faradyna.assessment.core.room.AssessmentDatabase
import com.faradyna.assessment.data.room.cart.CartDao
import com.faradyna.assessment.data.room.user.UserDao
import com.faradyna.assessment.utility.enums.LogCategory
import com.faradyna.assessment.utility.qualifiers.CartDaoQualifier
import com.faradyna.assessment.utility.qualifiers.UserDaoQualifier
import com.faradyna.assessment.utility.utils.logMessage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AssessmentDatabase {
        return Room.databaseBuilder(
            context,
            AssessmentDatabase::class.java,
            "room_assessment"
        ).setQueryCallback(
            { sqlQuery, bindArgs ->
                logMessage(LogCategory.ROOM, "SQL Query: $sqlQuery, SQL Args: ${bindArgs.joinToString(", ")}")
            },
            Executors.newSingleThreadExecutor()
        ).build()
    }

    @UserDaoQualifier
    @Provides
    @Singleton
    fun provideUserDao(
        database: AssessmentDatabase
    ): UserDao = database.userDao()

    @CartDaoQualifier
    @Provides
    @Singleton
    fun provideCartDao(
        database: AssessmentDatabase
    ): CartDao = database.cartDao()

}