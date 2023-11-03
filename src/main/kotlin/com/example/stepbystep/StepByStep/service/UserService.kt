package com.example.stepbystep.StepByStep.service

import com.example.stepbystep.StepByStep.dto.SensitiveUserData
import com.example.stepbystep.StepByStep.dto.UserDTO
import com.example.stepbystep.StepByStep.dto.toDTO
import com.example.stepbystep.StepByStep.dto.toEntity
import com.example.stepbystep.StepByStep.model.User
import com.example.stepbystep.StepByStep.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService @Autowired constructor(private val userRepository: UserRepository) {

    fun getAllUsers(): List<UserDTO> {
        return userRepository.findAll().map(User::toDTO)
    }

    fun getUserById(id: Long): UserDTO {
        return userRepository.findById(id).map(User::toDTO).orElseThrow {
            EntityNotFoundException("User with id $id not found")
        }
    }

    fun createUser(userDTO: UserDTO, sensitiveData: SensitiveUserData): UserDTO {
        validateUserDTO(userDTO)
        val user = userDTO.toEntity(sensitiveData)
        val savedUser = userRepository.save(user)
        return savedUser.toDTO()
    }

    fun updateUser(id: Long, userDTO: UserDTO, sensitiveData: SensitiveUserData): UserDTO {
        validateUserDTO(userDTO)
        val user = userRepository.findById(id).orElseThrow {
            EntityNotFoundException("User with id $id not found")
        }
        return userRepository.save(user.apply {
            username = userDTO.username
            email = userDTO.email
            profilePicture = userDTO.profilePicture
            provider = sensitiveData.provider
            providerId = sensitiveData.providerId
            refreshToken = sensitiveData.refreshToken
            // ... other fields
        }).toDTO()
    }

    fun deleteUser(id: Long) {
        if (!userRepository.existsById(id)) {
            throw EntityNotFoundException("User with id $id not found")
        }
        userRepository.deleteById(id)
    }

    fun findByEmail(email: String): UserDTO {
        return userRepository.findByEmail(email)?.toDTO()
            ?: throw EntityNotFoundException("User with email $email not found")
    }

    fun findByUsername(username: String): UserDTO {
        return userRepository.findByUsername(username)?.toDTO()
            ?: throw EntityNotFoundException("User with username $username not found")
    }

    private fun validateUserDTO(userDTO: UserDTO) {
        requireNotNull(userDTO.username) { "Username cannot be null" }
        require(userDTO.username.length in 3..20) { "Username must be between 3 and 20 characters" }

        requireNotNull(userDTO.email) { "Email cannot be null" }
        require(isValidEmail(userDTO.email)) { "Email is not in a valid format" }

        // If you need to ensure uniqueness, you might need to check against the database
        userRepository.findByEmail(userDTO.email)?.let {
            throw IllegalArgumentException("Email is already in use")
        }

        userRepository.findByUsername(userDTO.username)?.let {
            throw IllegalArgumentException("Username is already in use")
        }

        // Add any additional business rules here
    }

    private fun isValidEmail(email: String): Boolean {
        // Use a regex pattern to validate the email or a library function if available
        val emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        return email.matches(emailPattern)
    }

}


