package com.project.kopring.domain.user.service.Impl

import com.project.kopring.domain.user.entity.User
import com.project.kopring.domain.user.exception.InvalidTokenException
import com.project.kopring.domain.user.exception.RefreshTokenExpiredException
import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.domain.user.presentation.dto.request.ReissueTokenRequest
import com.project.kopring.domain.user.presentation.dto.response.NewTokenResponse
import com.project.kopring.domain.user.repository.UserRepository
import com.project.kopring.domain.user.service.ReissueService
import com.project.kopring.global.exception.ErrorCode
import com.project.kopring.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ReissueServiceImpl(
        private val jwtTokenProvider: JwtTokenProvider,
        private val userRepository: UserRepository
): ReissueService {

    @Transactional(rollbackFor = [Exception::class])
    override fun reissueToken(reissueTokenRequest: ReissueTokenRequest): NewTokenResponse {
        if(!jwtTokenProvider.isExpired(reissueTokenRequest.refreshToken)) {
            throw RefreshTokenExpiredException(ErrorCode.REFRESH_TOKEN_EXPIRED)
        }

        val user: User = userRepository.findByEmail(jwtTokenProvider.getUserEmail(reissueTokenRequest.refreshToken))
                ?: throw UserNotFoundException(ErrorCode.USER_NOT_FOUND)

        if(reissueTokenRequest.refreshToken != user.refreshToken) {
            throw InvalidTokenException(ErrorCode.INVALID_TOKEN)
        }

        val newAccessToken = jwtTokenProvider.generateAccessToken(user.email)
        val newRefreshToken = jwtTokenProvider.generateRefreshToken(user.email)

        user.updateRefreshToken(newRefreshToken)

        return NewTokenResponse(newAccessToken, newRefreshToken, jwtTokenProvider.getExpireAt())
    }

}