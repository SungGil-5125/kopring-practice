package com.project.kopring.domain.post.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.kopring.domain.post.presentation.data.request.PostRequest
import com.project.kopring.domain.user.domain.repository.UserRepository
import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.service.UserAuthService
import com.project.kopring.global.security.authentication.AuthDetailService
import com.project.kopring.global.security.jwt.JwtTokenProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.File
import java.io.FileInputStream
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest(
    @Autowired
    private val mockMvc: MockMvc,
    @Autowired
    private val userAuthService: UserAuthService,
    @Autowired
    private val objectMapper: ObjectMapper,
    @Autowired
    private val authDetailService: AuthDetailService,
    @Autowired
    private val jwtTokenProvider: JwtTokenProvider,
    @Autowired
    private val userRepository: UserRepository
) {

    private val email = "test5/@test.com"

    private val password = "test password"

    private val name = "test name"

    private val title = "test title"

    private val content = "test content"

    private val fileName = "testImage"

    private val contentType = "image/jpeg"

    private val filePath = "src/test/resources/image/testImage.jpeg"

    private val file = MockMultipartFile(
        fileName, "testImage.jpeg", contentType, FileInputStream(File(filePath))
    )

    fun getAccessToken(): String {
        val userDto = UserDto(
            userId = -1,
            email = email,
            password = password,
            name = name,
            file = null
        )

        userAuthService.signUp(userDto)

        val tokenResponse = userAuthService.signIn(userDto)

        val userDetails = authDetailService.loadUserByUsername(jwtTokenProvider.getUserEmail(tokenResponse.accessToken))
        val authentication = UsernamePasswordAuthenticationToken(userDetails, "", null)
        SecurityContextHolder.getContext().authentication = authentication

        return tokenResponse.accessToken
    }

    @AfterEach
    fun resetUser() {
        userRepository.deleteAll()
    }

    @Test
    fun `게시글 생성 테스트`() {
        // given
        val postRequest = PostRequest(
            title = title,
            content = content,
            tags = Collections.emptyList(),
        )

        val request = objectMapper.writeValueAsString(postRequest)

        val json = MockMultipartFile("request", "jsondata", "application/json", request.toByteArray())

        // when & then
        mockMvc.perform(multipart("/post")
            .file(file)
            .file(json)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

            .header("Authorization", getAccessToken())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8"))

            .andExpect(status().isCreated)
    }

}