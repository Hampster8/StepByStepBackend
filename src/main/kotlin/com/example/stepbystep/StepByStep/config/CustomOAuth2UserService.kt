package com.example.stepbystep.StepByStep.config

import com.example.stepbystep.StepByStep.model.User
import com.example.stepbystep.StepByStep.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(private val userRepository: UserRepository) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = DefaultOAuth2UserService().loadUser(userRequest)

        // Extract user details from oAuth2User and map to your User entity
        val attributes = oAuth2User.attributes
        val email = attributes["email"] as String
        val name = attributes["name"] as String

        // Check if user exists in your database, if not then create a new one
        val user = userRepository.findByEmail(email) ?: User(
            email = email,
            username = name,
            provider = userRequest.clientRegistration.registrationId,
            providerId = attributes["sub"] as String
        )

        if (user.id == 0L) {
            userRepository.save(user)
        }

        return oAuth2User
    }
}
