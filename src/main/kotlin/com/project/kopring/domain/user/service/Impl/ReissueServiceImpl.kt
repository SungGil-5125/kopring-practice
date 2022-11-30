package com.project.kopring.domain.user.service.Impl

import com.project.kopring.domain.user.domain.User
import com.project.kopring.domain.user.exception.InvalidTokenException
import com.project.kopring.domain.user.exception.RefreshTokenExpiredException
import com.project.kopring.domain.user.facade.UserFacade
import com.project.kopring.domain.user.presentation.dto.request.ReissueTokenRequest
import com.project.kopring.domain.user.presentation.dto.response.NewTokenResponse
import com.project.kopring.domain.user.service.ReissueService
import com.project.kopring.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReissueServiceImpl(
        private val jwtTokenProvider: JwtTokenProvider,
        private val userFacade: UserFacade
): ReissueService {

    @Transactional(rollbackFor = [Exception::class])
    override fun reissueToken(reissueTokenRequest: ReissueTokenRequest): NewTokenResponse {
        if(!jwtTokenProvider.isExpired(reissueTokenRequest.refreshToken)) {
            throw RefreshTokenExpiredException()
        }

        val user: User = userFacade.findUserByEmail(jwtTokenProvider.getUserEmail(reissueTokenRequest.refreshToken))

        if(reissueTokenRequest.refreshToken != user.refreshToken) {
            throw InvalidTokenException()
        }

        val newAccessToken = jwtTokenProvider.generateAccessToken(user.email)
        val newRefreshToken = jwtTokenProvider.generateRefreshToken(user.email)

        user.updateRefreshToken(newRefreshToken)

        return NewTokenResponse(newAccessToken, newRefreshToken, jwtTokenProvider.getExpireAt())
    }

}