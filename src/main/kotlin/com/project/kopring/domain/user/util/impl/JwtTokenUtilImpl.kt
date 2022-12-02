package com.project.kopring.domain.user.util.impl

import com.project.kopring.domain.user.presentation.data.response.TokenResponse
import com.project.kopring.domain.user.util.JwtTokenUtil
import com.project.kopring.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Component

@Component
class JwtTokenUtilImpl(
        private val jwtTokenProvider: JwtTokenProvider
): JwtTokenUtil {

    override fun generateJwtToken(email: String): TokenResponse {
        println(email)
        val accessToken = jwtTokenProvider.generateAccessToken(email)
        val refreshToken = jwtTokenProvider.generateRefreshToken(email)
        return TokenResponse(accessToken, refreshToken, jwtTokenProvider.getExpireAt())
    }

}