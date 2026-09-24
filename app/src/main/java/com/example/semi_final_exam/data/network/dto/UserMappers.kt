package com.example.semi_final_exam.data.network.dto

import com.example.semi_final_exam.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = id ?: "",
        fullName = fullname?.trim() ?: "(no name)",
        email = email?.trim() ?: "",
        birthdate = birthdate ?: "(not set)"
    )
}
