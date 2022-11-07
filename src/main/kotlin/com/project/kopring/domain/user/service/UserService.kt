package com.project.kopring.domain.user.service

import com.project.kopring.domain.user.exception.DuplicateEmailException
import com.project.kopring.domain.user.exception.PasswordNotCorrectException
import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.domain.user.presentation.dto.SignInRequest
import com.project.kopring.domain.user.presentation.dto.SignUpRequest
import com.project.kopring.domain.user.repository.UserRepository
import com.project.kopring.global.exception.ErrorCode
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) {

    @Transactional(rollbackFor = [Exception::class])
    fun signUp(signUpRequest: SignUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email)) {
            throw DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL)
        }
        userRepository.save(signUpRequest.toEntity(passwordEncoder.encode(signUpRequest.password)))
    }

    @Transactional(rollbackFor = [Exception::class])
    fun signIn(signInRequest: SignInRequest) {
        val user = (userRepository.findByEmail(signInRequest.email)
                ?: throw UserNotFoundException(ErrorCode.NOT_FOUND_USER))

        if (!passwordEncoder.matches(signInRequest.password, user.password)) {
            throw PasswordNotCorrectException(ErrorCode.PASSWORD_NOT_CORRECT)
        }
    }
}