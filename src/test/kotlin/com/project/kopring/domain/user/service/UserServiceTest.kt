package com.project.kopring.domain.user.service

import com.project.kopring.domain.user.presentation.data.request.SignUpRequest
import com.project.kopring.domain.user.domain.repository.UserRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest(
        private val signUpService: SignUpService,
        private val signInService: SignInService,
        private val reissueService: ReissueService,
        private val userRepository: UserRepository
) {

    @Test
    @DisplayName("회원가입 테스트")
    fun signUpTest() {

        // given
        val signUpRequest = SignUpRequest("sunggil0125@naver.com", "1234", "김성길")

        // when

        // then

    }
}