package com.project.kopring.domain.user.service.Impl

import com.project.kopring.domain.user.exception.PasswordNotCorrectException
import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.domain.user.presentation.dto.request.SignInRequest
import com.project.kopring.domain.user.presentation.dto.response.SignInResponse
import com.project.kopring.domain.user.repository.UserRepository
import com.project.kopring.domain.user.service.SignInService
import com.project.kopring.global.exception.ErrorCode
import com.project.kopring.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class SignInServiceImpl(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val jwtTokenProvider: JwtTokenProvider
): SignInService {

    @Transactional(rollbackFor = [Exception::class])
    override fun signIn(signInRequest: SignInRequest): SignInResponse {
        val user = userRepository.findByEmail(signInRequest.email)
                ?: throw UserNotFoundException(ErrorCode.USER_NOT_FOUND)

        if (!passwordEncoder.matches(signInRequest.password, user.password)) {
                throw PasswordNotCorrectException(ErrorCode.PASSWORD_NOT_CORRECT)
        }
        val accessToken = jwtTokenProvider.generateAccessToken(user.email)
        val refreshToken = jwtTokenProvider.generateRefreshToken(user.email)
        user.updateRefreshToken(refreshToken)
        return SignInResponse(accessToken, refreshToken, jwtTokenProvider.getExpireAt())
    }

}