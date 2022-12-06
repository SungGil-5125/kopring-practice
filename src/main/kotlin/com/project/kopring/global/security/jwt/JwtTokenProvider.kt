package com.project.kopring.global.security.jwt

import com.project.kopring.global.security.jwt.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.time.LocalDateTime
import java.util.*

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {
    private val ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 3L // 3시간
    private val REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L // 1주

    enum class TokenType(val value: String) {
        ACCESS_TOKEN("accessToken"),
        REFRESH_TOKEN("refreshToken")
    }

    private fun getSignInKey(secretKey: String): Key {
        val bytes = secretKey.toByteArray(StandardCharsets.UTF_8)
        return Keys.hmacShaKeyFor(bytes)
    }

    fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getSignInKey(jwtProperties.key))
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun getUserEmail(token: String): String = extractAllClaims(token).subject

    fun isExpired(token: String): Boolean {
        runCatching {
            extractAllClaims(token).expiration
        }.onFailure {
            return true
        }
        return false
    }

    fun getExpireAt(): LocalDateTime = LocalDateTime.now().plusSeconds(ACCESS_TOKEN_EXPIRE_TIME / 1000)

    fun doGenerateToken(email: String, tokenType: TokenType, expiredTime: Long): String {
        return Jwts.builder()
            .setSubject(email)
            .claim("tokenType", tokenType.value)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiredTime))
            .signWith(getSignInKey(jwtProperties.key))
            .compact()
    }

    fun generateAccessToken(email: String): String =
        doGenerateToken(email, TokenType.ACCESS_TOKEN, ACCESS_TOKEN_EXPIRE_TIME)

    fun generateRefreshToken(email: String): String =
        doGenerateToken(email, TokenType.REFRESH_TOKEN, REFRESH_TOKEN_EXPIRE_TIME)

}