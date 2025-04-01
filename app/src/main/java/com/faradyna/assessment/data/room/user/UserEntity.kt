package com.faradyna.assessment.data.room.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faradyna.assessment.domain.room.user.UserEntityDomain

@Entity(
    tableName = "users",
)
data class UserEntity(
    @PrimaryKey val id: Int,
    val username: String,
    val email: String,
    val firstname: String,
    val lastname: String,
    val phone: String,
) {
    fun toDomain() = UserEntityDomain(
        id = id,
        username = username,
        email = email,
        firstname = firstname,
        lastname = lastname,
        phone = phone,
    )
}