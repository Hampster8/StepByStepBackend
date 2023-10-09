package com.example.stepbystep.StepByStep.controller

import com.example.stepbystep.StepByStep.dto.LeaderboardEntryDTO
import com.example.stepbystep.StepByStep.dto.StepRecordDTO
import com.example.stepbystep.StepByStep.dto.UserDTO
import com.example.stepbystep.StepByStep.model.StepRecord
import com.example.stepbystep.StepByStep.model.User
import com.example.stepbystep.StepByStep.repository.StepRecordRepository
import com.example.stepbystep.StepByStep.repository.UserRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/api")
class StepController(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val stepRecordRepository: StepRecordRepository
) {


    @PostMapping("/users")
    fun createUser(@Valid @RequestBody user: User, bindingResult: BindingResult): ResponseEntity<Any> {
        val validationErrors = validateUser(bindingResult)
        if (validationErrors.isNotEmpty()) {
            return ResponseEntity.badRequest().body(mapOf("errors" to validationErrors))
        }

        if (isEmailDuplicate(user.email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to "Email already exists"))
        }

        return try {
            val userDTO = saveUserAndMapToDTO(user)
            ResponseEntity.ok(userDTO)
        } catch (e: DataIntegrityViolationException) {
            ResponseEntity.badRequest().body(mapOf("error" to "Data integrity violation"))
        }
    }

    private fun validateUser(bindingResult: BindingResult): List<Map<String, String>> {
        return if (bindingResult.hasErrors()) {
            bindingResult.fieldErrors.map {
                mapOf("field" to it.field, "message" to (it.defaultMessage ?: "Unknown error"))
            }
        } else {
            emptyList()
        }
    }

    private fun isEmailDuplicate(email: String): Boolean {
        return userRepository.findByEmail(email) != null
    }

    private fun saveUserAndMapToDTO(user: User): UserDTO {
        val newUser = userRepository.save(user)
        return UserDTO(newUser.id, newUser.username, newUser.email, newUser.provider, newUser.providerId)
    }

    @GetMapping("/users")
    fun getUsers(): ResponseEntity<List<UserDTO>> {
        val users = userRepository.findAll()
        val userDTOs = users.map { UserDTO(it.id, it.username, it.email, it.provider, it.providerId) }
        return ResponseEntity.ok(userDTOs)
    }


    @PostMapping("/steps")
    fun recordSteps(@RequestBody stepRecord: StepRecord): ResponseEntity<StepRecordDTO> {
        if (stepRecord.steps <= 0) {
            return ResponseEntity.badRequest().build()
        }

        val user = userRepository.findById(stepRecord.user.id).orElse(null) ?: return ResponseEntity.notFound().build()

        val newStepRecord = StepRecord(user = user, steps = stepRecord.steps, date = stepRecord.date)
        val savedStepRecord = stepRecordRepository.save(newStepRecord)
        val stepRecordDTO = StepRecordDTO(savedStepRecord.id, savedStepRecord.user.id, savedStepRecord.steps, savedStepRecord.date)

        return ResponseEntity.ok(stepRecordDTO)
    }


    @GetMapping("/leaderboard")
    fun getLeaderboard(): ResponseEntity<List<LeaderboardEntryDTO>> {
        val leaderboard = stepRecordRepository.findLeaderboardEntries()
        return ResponseEntity.ok(leaderboard)
    }



    @GetMapping("/steps/{userId}/{date}")
    fun getStepsForUserOnDate(
        @PathVariable userId: Long,
        @PathVariable date: LocalDate
    ): ResponseEntity<List<StepRecordDTO>> {
        val user = userRepository.findById(userId).orElse(null) ?: return ResponseEntity.notFound().build()

        val stepRecords = stepRecordRepository.findByUserIdAndDate(userId, date)
        if (stepRecords.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        val stepRecordDTOs = stepRecords.map { StepRecordDTO(it.id, it.user.id, it.steps, it.date) }
        return ResponseEntity.ok(stepRecordDTOs)
    }

    // More endpoints can be added here for other functionalities...
}
