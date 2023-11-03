package com.example.stepbystep.StepByStep.model

import jakarta.persistence.*
import java.security.AuthProvider
import java.time.LocalDateTime

@Entity
@Table(name = "app_user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 20)
    val username: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Enumerated(EnumType.STRING)
    val provider: AuthProvider,

    @Column(nullable = false)
    val providerId: String,

    val refreshToken: String?,

    val profilePicture: String?,

    val isSharingData: Boolean = true,

    val isActive: Boolean = true,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val dailyStepCounts: Set<DailyStepCount> = HashSet(),

    @ManyToMany
    @JoinTable(
        name = "user_leaderboards",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "leaderboard_id")]
    )
    val customLeaderboards: Set<Leaderboard> = HashSet(),

    // Additional fields and relationships can be added here

    @Column(nullable = false)
    val created: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val updated: LocalDateTime = LocalDateTime.now()
) {
    // If you need to perform any operations before persisting or updating the entity,
    // you can use JPA lifecycle callbacks like @PrePersist and @PreUpdate

    @PrePersist
    fun onPrePersist() {
        // Logic to be executed before persisting a new entity
    }

    @PreUpdate
    fun onPreUpdate() {
        // Logic to be executed before updating an existing entity
    }
}
