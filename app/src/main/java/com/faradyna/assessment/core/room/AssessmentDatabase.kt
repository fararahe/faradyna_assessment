package com.faradyna.assessment.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.faradyna.assessment.data.room.cart.CartDao
import com.faradyna.assessment.data.room.cart.CartEntity
import com.faradyna.assessment.data.room.user.UserDao
import com.faradyna.assessment.data.room.user.UserEntity

@Database(
    entities = [
        UserEntity::class,
        CartEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AssessmentDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao
}