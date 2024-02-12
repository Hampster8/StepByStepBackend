package com.example.stepbystep.StepByStep.dto

import com.example.stepbystep.StepByStep.model.User
import java.security.AuthProvider

data class UserDTO(
    val id: Long?,
    val username: String,
    val email: String,
    val profilePicture: String?
)

fun User.toDTO(): UserDTO {
    return UserDTO(
        id = id,
        username = username,
        email = email,
        profilePicture = profilePicture
    )
}

fun UserDTO.toEntity(sensitiveData: SensitiveUserData): User {
    return User(
        id = this.id ?: 0, // If id is null, use 0 or some other default value
        username = this.username,
        email = this.email,
        profilePicture = this.profilePicture,
        provider = sensitiveData.provider,
        providerId = sensitiveData.providerId,
        refreshToken = sensitiveData.refreshToken
        // Initialize other fields if necessary
    )
}

data class SensitiveUserData(
    val provider: AuthProvider,
    val providerId: String,
    val refreshToken: String?
)
