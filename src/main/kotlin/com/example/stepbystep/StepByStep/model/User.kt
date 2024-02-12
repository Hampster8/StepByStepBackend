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
    var username: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Enumerated(EnumType.STRING)
    var provider: AuthProvider,

    @Column(nullable = false)
    var providerId: String,

    var refreshToken: String?,

    var profilePicture: String?,

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
    var created: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updated: LocalDateTime = LocalDateTime.now()
) {
    // If you need to perform any operations before persisting or updating the entity,
    // you can use JPA lifecycle callbacks like @PrePersist and @PreUpdate

    @PrePersist
    fun onPrePersist() {
        created = LocalDateTime.now()
        updated = LocalDateTime.now()
    }

    @PreUpdate
    fun onPreUpdate() {
        updated = LocalDateTime.now()
    }

}
