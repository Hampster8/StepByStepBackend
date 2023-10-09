package com.example.stepbystep.StepByStep.dto

import java.time.LocalDate

data class StepRecordDTO(val id: Long, val userId: Long, val steps: Int, val date: LocalDate)
