package com.project.kopring.domain.user.utils.impl

import com.project.kopring.domain.user.domain.repository.UserRepository
import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.domain.user.presentation.data.response.TokenResponse
import com.project.kopring.domain.user.utils.JwtTokenUtil
import com.project.kopring.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Component

@Component
class JwtTokenUtilImpl(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository
) : JwtTokenUtil {

    override fun generateJwtToken(email: String): TokenResponse {
        val user = userRepository.findByEmail(email)
            ?: throw UserNotFoundException()
        val accessToken = jwtTokenProvider.generateAccessToken(email)
        val refreshToken = jwtTokenProvider.generateRefreshToken(email)
        user.updateRefreshToken(refreshToken)
        return TokenResponse(accessToken, refreshToken, jwtTokenProvider.getExpireAt())
    }

}