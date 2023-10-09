package com.example.stepbystep.StepByStep.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class StepRecord(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    val steps: Int,
    val date: LocalDate
)