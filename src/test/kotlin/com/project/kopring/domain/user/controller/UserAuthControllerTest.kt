package com.project.kopring.domain.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.kopring.domain.user.presentation.data.request.SignInRequest
import com.project.kopring.domain.user.presentation.data.request.SignUpRequest
import com.project.kopring.domain.user.service.UserAuthService
import com.project.kopring.domain.user.utils.AccountConverter
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class UserAuthControllerTest(
    @Autowired
    private val mockMvc: MockMvc,
    @Autowired
    private val userAuthService: UserAuthService,
    @Autowired
    private val accountConverter: AccountConverter,
    @Autowired
    private val objectMapper: ObjectMapper
) {

    private val email = "test@test.com"

    private val password = "test password"

    private val name = "test name"

    @Test
    fun `회원가입 테스트`() {
        // given
        val signUpRequest = SignUpRequest(
            email = email,
            password = password,
            name = name
        )
        val request = objectMapper.writeValueAsString(signUpRequest)

        // when & then
        mockMvc.perform(post("/auth/signup")
            .content(request)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8"))

            .andExpect(status().isCreated)
    }

    @Test
    fun `로그인 테스트`() {
        // given
        val signInRequest = SignInRequest(
            email = email,
            password = password,
        )
        val request = objectMapper.writeValueAsString(signInRequest)

        // when & then
        mockMvc.perform(post("/auth/signin")
            .content(request)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8"))

            .andExpect(status().isOk)
            .andExpect(jsonPath("$.accessToken").isNotEmpty)
            .andExpect(jsonPath("$.refreshToken").isNotEmpty)
            .andExpect(jsonPath("$.expiredAt").isNotEmpty)
    }

    @Test
    fun `토큰 재발급 테스트`() {
        // given
        val signInRequest = SignInRequest(
            email = email,
            password = password,
        )

        val tokenResponse = userAuthService.signIn(accountConverter.toDto(signInRequest))

        // when & then
        mockMvc.perform(patch("/auth/reissue")
            .header("RefreshToken", tokenResponse.refreshToken)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8"))

            .andExpect(status().isOk)
            .andExpect(jsonPath("$.accessToken").isNotEmpty)
            .andExpect(jsonPath("$.refreshToken").isNotEmpty)
            .andExpect(jsonPath("$.expiredAt").isNotEmpty)
    }

}