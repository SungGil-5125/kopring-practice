package com.project.kopring.domain.user.service.impl

import com.project.kopring.domain.user.domain.repository.UserRepository
import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.domain.user.presentation.data.dto.ReissueTokenDto
import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.presentation.data.response.TokenResponse
import com.project.kopring.domain.user.presentation.data.type.ValidatorType
import com.project.kopring.domain.user.service.UserAccountService
import com.project.kopring.domain.user.util.AccountConverter
import com.project.kopring.domain.user.util.AccountValidator
import com.project.kopring.domain.user.util.JwtTokenUtil
import com.project.kopring.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAccountServiceImpl(
        private val accountConverter: AccountConverter,
        private val accountValidator: AccountValidator,
        private val userRepository: UserRepository,
        private val jwtTokenUtil: JwtTokenUtil,
        private val jwtTokenProvider: JwtTokenProvider,
        private val passwordEncoder: PasswordEncoder
): UserAccountService {

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
    override fun reissueToken(reissueTokenDto: ReissueTokenDto): TokenResponse =
         userRepository.findByEmail(jwtTokenProvider.getUserEmail(reissueTokenDto.refreshToken))
             .let { it ?: throw UserNotFoundException() }
             .let { reissueTokenDto.refreshToken != it.refreshToken}
             .let { jwtTokenUtil.generateJwtToken(jwtTokenProvider.getUserEmail(reissueTokenDto.refreshToken)) }
             .let { TokenResponse(it.accessToken, it.refreshToken, it.expiredAt) }

}