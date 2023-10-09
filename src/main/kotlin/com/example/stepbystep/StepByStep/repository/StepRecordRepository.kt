package com.example.stepbystep.StepByStep.repository

import com.example.stepbystep.StepByStep.dto.LeaderboardEntryDTO
import com.example.stepbystep.StepByStep.model.StepRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface StepRecordRepository : JpaRepository<StepRecord, Long> {
    fun findByUserId(userId: Long): List<StepRecord>
    fun findByDateBetween(startDate: LocalDate, endDate: LocalDate): List<StepRecord>
    fun findByUserIdAndDate(userId: Long, date: LocalDate): List<StepRecord>

    @Query("SELECT new com.example.stepbystep.StepByStep.dto.LeaderboardEntryDTO(sr.user.id, sr.user.username, SUM(sr.steps)) " +
            "FROM StepRecord sr " +
            "GROUP BY sr.user.id, sr.user.username " +
            "ORDER BY SUM(sr.steps) DESC")
    fun findLeaderboardEntries(): List<LeaderboardEntryDTO>
}
