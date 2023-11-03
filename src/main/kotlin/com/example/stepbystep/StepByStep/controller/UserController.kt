package com.example.stepbystep.StepByStep.controller

import com.example.stepbystep.StepByStep.dto.SensitiveUserData
import com.example.stepbystep.StepByStep.dto.UserDTO
import com.example.stepbystep.StepByStep.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController @Autowired constructor(
    private val userService: UserService
) {

    // Get all users
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDTO>> {
        val users = userService.getAllUsers()
        return ResponseEntity(users, HttpStatus.OK)
    }

    // Get a single user by ID
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDTO> {
        val userDTO = userService.getUserById(id)
        return ResponseEntity(userDTO, HttpStatus.OK)
    }

    // Create a new user
    @PostMapping
    fun createUser(@Valid @RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        // You would need to pass the SensitiveUserData from the client or handle it differently
        val sensitiveData = /* extract from request or another source */
        val createdUser = userService.createUser(userDTO, sensitiveData)
        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }

    // Update an existing user
    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @Valid @RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        // You would need to pass the SensitiveUserData from the client or handle it differently
        val sensitiveData = /* extract from request or another source */
        val updatedUser = userService.updateUser(id, userDTO, sensitiveData)
        return ResponseEntity(updatedUser, HttpStatus.OK)
    }

    // Delete a user
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    // Additional endpoints can be added below
    // ...
}
