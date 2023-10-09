package com.example.stepbystep.StepByStep.controller

import com.example.stepbystep.StepByStep.repository.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest(properties = ["spring.config.name=application-test"])
@AutoConfigureMockMvc
class StepControllerTests(@Autowired val mockMvc: MockMvc) {

    @MockBean
    lateinit var userRepository: UserRepository

    @Test
    @WithMockUser(username = "testUser", roles = ["USER"])
    fun `Create user returns 200 and user data`() {
        val userJson = """{
            "username": "testUser",
            "email": "test@example.com"
        }"""

        mockMvc.post("/api/users") {
            contentType = MediaType.APPLICATION_JSON
            content = userJson
            with(csrf())
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.username") { value("testUser") }
            jsonPath("$.email") { value("test@example.com") }
        }
    }

    // ... [Other Tests Here] ...

}
