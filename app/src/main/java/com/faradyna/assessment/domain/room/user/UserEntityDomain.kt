package com.faradyna.assessment.domain.room.user

data class UserEntityDomain(
    val id: Int,
    val username: String,
    val email: String,
    val firstname: String,
    val lastname: String,
    val phone: String,
) {
    fun getName(): String {
        return "${firstname.replaceFirstChar { it.uppercase() }} ${lastname.replaceFirstChar { it.uppercase() }}"
    }
}