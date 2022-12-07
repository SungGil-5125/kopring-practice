package com.project.kopring.domain.user.service.impl

import com.project.kopring.domain.user.domain.User
import com.project.kopring.domain.user.domain.repository.UserRepository
import com.project.kopring.domain.user.exception.InvalidTokenException
import com.project.kopring.domain.user.exception.RefreshTokenExpiredException
import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.domain.user.presentation.data.dto.ReissueTokenDto
import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.presentation.data.response.TokenResponse
import com.project.kopring.domain.user.presentation.data.type.ValidatorType
import com.project.kopring.domain.user.service.UserAuthService
import com.project.kopring.domain.user.utils.AccountConverter
import com.project.kopring.domain.user.utils.AccountValidator
import com.project.kopring.domain.user.utils.JwtTokenUtil
import com.project.kopring.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAuthServiceImpl(
    private val accountConverter: AccountConverter,
    private val accountValidator: AccountValidator,
    private val userRepository: UserRepository,
    private val jwtTokenUtil: JwtTokenUtil,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder
) : UserAuthService {

    @Transactional(rollbackFor = [Exception::class])
    override fun signUp(userDto: UserDto) {
        accountValidator.validate(ValidatorType.SIGNUP, userDto)
            .let { accountConverter.toEntity(userDto, passwordEncoder.encode(userDto.password)) }
            .let { userRepository.save(it) }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun signIn(userDto: UserDto): TokenResponse =
        accountValidator.validate(ValidatorType.SIGNIN, userDto)
            .let { jwtTokenUtil.generateJwtToken(userDto.email) }

    @Transactional(rollbackFor = [Exception::class])
    override fun reissueToken(reissueTokenDto: ReissueTokenDto): TokenResponse {
        val user = userRepository.findByEmail(jwtTokenProvider.getUserEmail(reissueTokenDto.refreshToken))
            ?: throw UserNotFoundException()

        return jwtTokenUtil.generateJwtToken(tokenIsValid(user, reissueTokenDto.refreshToken))
            .let { TokenResponse(it.accessToken, it.refreshToken, it.expiredAt) }
    }

    private fun tokenIsValid(user: User, refreshToken: String): String {

        when (refreshToken) {
            user.refreshToken -> {
                jwtTokenProvider.isExpired(refreshToken)
                    .let {
                        if (it) {
                            throw RefreshTokenExpiredException()
                        }
                    }
            }

            else -> throw InvalidTokenException()
        }

        return user.email
    }

}