package com.project.kopring.domain.user.service.Impl

import com.project.kopring.domain.user.exception.DuplicateEmailException
import com.project.kopring.domain.user.presentation.dto.request.SignUpRequest
import com.project.kopring.domain.user.repository.UserRepository
import com.project.kopring.domain.user.service.SignUpService
import com.project.kopring.global.exception.ErrorCode
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class SignUpServiceImpl(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
): SignUpService{

    @Transactional(rollbackFor = [Exception::class])
    override fun signUp(signUpRequest: SignUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email)) {
            throw DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL)
        }
        userRepository.save(signUpRequest.toEntity(passwordEncoder.encode(signUpRequest.password)))
    }

}