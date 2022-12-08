package com.project.kopring.domain.user.service

import com.project.kopring.domain.user.domain.repository.UserRepository
import com.project.kopring.domain.user.exception.DuplicateEmailException
import com.project.kopring.domain.user.exception.PasswordNotCorrectException
import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.domain.user.presentation.data.dto.ReissueTokenDto
import com.project.kopring.domain.user.presentation.data.dto.UserDto
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class UserAuthServiceTest(
    @Autowired
    private val userAuthService: UserAuthService,

    @Autowired
    private val userRepository: UserRepository
) {

    private val email = "test@test.com"

    private val password = "test password"

    private val name = "test name"

    @AfterEach
    fun init() {
        userRepository.deleteAll();
    }

    @Test
    fun `회원가입 테스트`() {
        // given
        val userDto = UserDto(
            userId = -1,
            email = email,
            password = password,
            name = name,
            file = null
        )

        // when
        userAuthService.signUp(userDto)

        // then
        assertDoesNotThrow {
            userRepository.findByEmail(userDto.email)
        }

    }

    @Test
    fun `중복된 이메일로 회원가입을 할 경우`() {
        // given
        val userDto = UserDto(
            userId = -1,
            email = email,
            password = password,
            name = name,
            file = null
        )

        // when & then
        assertThrows<DuplicateEmailException> {
            userAuthService.signUp(userDto)
        }
    }

    @Test
    fun `로그인 테스트`() {
        // given
        val userDto = UserDto(
            userId = -1,
            email = email,
            password = password,
            name = name,
            file = null
        )

        // when & then
        Assertions.assertNotNull(userAuthService.signIn(userDto))
    }

    @Test
    fun `등록된 이메일이 없는데 로그인을 한 경우`() {
        // given
        val userDto = UserDto(
            userId = -1,
            email = "test1@test.com",
            password = password,
            name = name,
            file = null
        )

        // when & then
        assertThrows<UserNotFoundException> {
            userAuthService.signIn(userDto)
        }
    }

    @Test
    fun `비밀번호가 틀리게 로그인을 한 경우`() {
        // given
        val userDto = UserDto(
            userId = -1,
            email = email,
            password = "test1 password",
            name = name,
            file = null
        )

        // when & then
        assertThrows<PasswordNotCorrectException> {
            userAuthService.signIn(userDto)
        }
    }

    @Test
    fun `토큰 재발급 테스트`() {
        // given
        val userDto = UserDto(
            userId = -1,
            email = email,
            password = password,
            name = name,
            file = null
        )

        val reissueTokenDto = ReissueTokenDto(
            refreshToken = userAuthService.signIn(userDto).refreshToken
        )

        // when & then
        Assertions.assertNotNull(userAuthService.reissueToken(reissueTokenDto))
    }
}