package com.project.kopring.domain.user.facade

import com.project.kopring.domain.user.entity.User
import com.project.kopring.domain.user.exception.PasswordNotCorrectException
import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.domain.user.presentation.dto.request.SignUpRequest
import com.project.kopring.domain.user.presentation.dto.request.toEntity
import com.project.kopring.domain.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserFacade(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) {

    fun findUserByEmail(email: String): User =
            userRepository.findByEmail(email)
                    ?: throw UserNotFoundException()

    fun findUserById(userId: Long): User =
            userRepository.findById(userId)
                    .orElseThrow() { throw UserNotFoundException() }

    fun existsByEmail(email: String): Boolean =
            userRepository.existsByEmail(email)

    fun checkPassword(user: User, password: String) {
        if (!passwordEncoder.matches(password, user.password))
            throw PasswordNotCorrectException()
    }

    fun currentUser(): User {
        val email = SecurityContextHolder.getContext().authentication.name
        return findUserByEmail(email)
    }

    fun saveUser(signUpRequest: SignUpRequest) {
        val password = passwordEncoder.encode(signUpRequest.password)
        userRepository.save(signUpRequest.toEntity(password))
    }

}