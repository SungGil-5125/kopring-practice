package com.project.kopring.domain.comment.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.kopring.domain.comment.presentation.data.request.CommentRequest
import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.service.UserAuthService
import com.project.kopring.global.security.authentication.AuthDetailService
import com.project.kopring.global.security.jwt.JwtTokenProvider
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest(
    @Autowired
    private val mockMvc: MockMvc,
    @Autowired
    private val userAuthService: UserAuthService,
    @Autowired
    private val objectMapper: ObjectMapper,
    @Autowired
    private val authDetailService: AuthDetailService,
    @Autowired
    private val jwtTokenProvider: JwtTokenProvider
) {

    private val email = "test5/@test.com"

    private val password = "test password"

    private val name = "test name"

    private val comment = "test comment"

    fun getAccessToken(): String {
        val userDto = UserDto(
            userId = -1,
            email = email,
            password = password,
            name = name,
            file = null
        )

        val tokenResponse = userAuthService.signIn(userDto)

        val userDetails = authDetailService.loadUserByUsername(jwtTokenProvider.getUserEmail(tokenResponse.accessToken))
        val authentication = UsernamePasswordAuthenticationToken(userDetails, "", null)
        SecurityContextHolder.getContext().authentication = authentication

        return tokenResponse.accessToken
    }

    @Test
    fun `댓글 생성 테스트`() {
        // given
        val commentRequest = CommentRequest(
            comment = comment
        )
        val request = objectMapper.writeValueAsString(commentRequest)

        // when & then
        mockMvc.perform(post("/comment/5")
            .content(request)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getAccessToken())
            .characterEncoding("UTF-8"))

            .andExpect(status().isCreated)
    }

    @Test
    fun `댓글 수정 테스트`() {
        // given
        val commentRequest = CommentRequest(
            comment = comment
        )
        val request = objectMapper.writeValueAsString(commentRequest)

        // when & then
        mockMvc.perform(patch("/comment/3")
            .content(request)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getAccessToken())
            .characterEncoding("UTF-8"))

            .andExpect(status().isOk)
    }

    @Test
    fun `댓글 삭제 테스트`() {

        mockMvc.perform(delete("/comment/3")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getAccessToken())
            .characterEncoding("UTF-8"))

            .andExpect(status().isOk)

    }

}