package com.example.stepbystep.StepByStep.model

import jakarta.persistence.*
import java.time.LocalDateTime

enum class LeaderboardType {
    GLOBAL, COMPANY, CUSTOM
}

@Entity
@Table(name = "leaderboard")
data class Leaderboard(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @Column(unique = true)
    val name: String,

    val description: String,

    @Enumerated(EnumType.STRING)
    val type: LeaderboardType,

    val creationTimestamp: LocalDateTime = LocalDateTime.now(),

    val lastUpdatedTimestamp: LocalDateTime = LocalDateTime.now(),

    val joinCode: String?, // Unique code or QR code for joining the leaderboard

    val isPublic: Boolean = true, // Determines if the leaderboard is public or private

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    val owner: User, // The user who created the leaderboard

    @ManyToMany(mappedBy = "customLeaderboards")
    val users: Set<User> = HashSet(),

    @OneToMany(mappedBy = "leaderboard", fetch = FetchType.LAZY)
    val records: Set<DailyStepCount> = HashSet()


)
