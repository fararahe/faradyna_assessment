package com.faradyna.assessment.domain.auth.resp

data class UserDomain(
    val address: AddressDomain?,
    val email: String,
    val id: Int,
    val name: NameDomain?,
    val password: String,
    val phone: String,
    val username: String,
    val v: Int
)