package com.example.stepbystep.StepByStep.model

import com.example.stepbystep.StepByStep.util.StrictEmail
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank



@Entity
@Table(name = "app_user") // 'user' is a reserved keyword in Postgres
data class User(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @Column(unique = true)
    @get: jakarta.validation.constraints.Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    val username: String,

    @get: StrictEmail(message = "Email is invalid")
    @get: jakarta.validation.constraints.Size(max = 50, message = "Email must be less than 50 characters")
    @Column(unique = true)
    val email: String,


    val provider: String,

    @Column(unique = true)
    val providerId: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val stepRecords: Set<StepRecord> = HashSet()
)