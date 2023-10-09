package com.example.stepbystep.StepByStep.dto

data class UserDTO(
    val id: Long,
    val username: String,
    val email: String,
    val provider: String,
    val providerId: String
)
