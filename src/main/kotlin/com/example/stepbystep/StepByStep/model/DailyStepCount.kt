package com.example.stepbystep.StepByStep.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

@Entity
data class DailyStepCount(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "user_id")
    val user: User<Any?>,

    @get: NotNull(message = "Steps cannot be null")
    @get: jakarta.validation.constraints.Min(0, message = "Steps cannot be negative")
    val steps: Int,
    val date: LocalDate
)