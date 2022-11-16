package com.project.kopring.domain.user.service.Impl

import com.project.kopring.domain.user.facade.UserFacade
import com.project.kopring.domain.user.presentation.dto.request.SignInRequest
import com.project.kopring.domain.user.presentation.dto.response.SignInResponse
import com.project.kopring.domain.user.service.SignInService
import com.project.kopring.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignInServiceImpl(
        private val userFacade: UserFacade,
        private val jwtTokenProvider: JwtTokenProvider
): SignInService {

    @Transactional(rollbackFor = [Exception::class])
    override fun signIn(signInRequest: SignInRequest): SignInResponse {
        val user = userFacade.findUserByEmail(signInRequest.email)

        userFacade.checkPassword(user, signInRequest.password)

        val accessToken = jwtTokenProvider.generateAccessToken(user.email)
        val refreshToken = jwtTokenProvider.generateRefreshToken(user.email)

        user.updateRefreshToken(refreshToken)

        return SignInResponse(accessToken, refreshToken, jwtTokenProvider.getExpireAt())
    }

}