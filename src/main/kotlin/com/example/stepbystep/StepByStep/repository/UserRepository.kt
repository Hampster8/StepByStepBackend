package com.example.stepbystep.StepByStep.repository

import com.example.stepbystep.StepByStep.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    // Custom query methods can be added here if needed

    // Example: Find a user by email
    fun findByEmail(email: String): User?

    // Example: Find a user by username
    fun findByUsername(username: String): User?
}
