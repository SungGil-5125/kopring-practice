package com.project.kopring.domain.post.service

import com.project.kopring.domain.post.domain.repository.PostRepository
import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.data.dto.PostKeywordDto
import com.project.kopring.domain.user.domain.repository.UserRepository
import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.service.UserAuthService
import com.project.kopring.global.security.authentication.AuthDetailService
import com.project.kopring.global.security.jwt.JwtTokenProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.io.File
import java.io.FileInputStream
import java.util.*

@SpringBootTest
class PostServiceTest(
    @Autowired
    private val postService: PostService,
    @Autowired
    private val postRepository: PostRepository,
    @Autowired
    private val userAuthService: UserAuthService,
    @Autowired
    private val authDetailService: AuthDetailService,
    @Autowired
    private val jwtTokenProvider: JwtTokenProvider,
    @Autowired
    private val userRepository: UserRepository
) {

    private val email = "test@test.com"

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

    @BeforeEach
    fun initUser() {
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
    }

    @AfterEach
    fun resetUser() {
        userRepository.deleteAll()
    }

    @Test
    fun `게시글 생성 테스트`() {
        // given
        val postDto = PostDto(
            id = -1,
            title = title,
            content = content,
            tags = Collections.emptyList(),
            file = file
        )

        // when & then
        assertDoesNotThrow {
            postService.writePost(postDto)
        }
    }

    @Test
    fun `게시글 수정 테스트`() {
        // given
        val postDto = PostDto(
            id = 3,
            title = title,
            content = content,
            tags = Collections.emptyList(),
            file = null
        )

        // when & then
        assertDoesNotThrow {
            postService.updatePost(postDto)
        }
    }

    @Test
    fun `게시글 삭제 테스트`() {
        // given
        val postDto = PostDto(
            id = 3,
            title = "",
            content = "",
            tags = Collections.emptyList(),
            file = null
        )

        // when & then
        assertDoesNotThrow {
            postService.deletePost(postDto)
        }
    }

    @Test
    fun `게시글 상세보기 테스트`() {
        // given
        val postDto = PostDto(
            id = 4,
            title = "",
            content = "",
            tags = Collections.emptyList(),
            file = null
        )

        // when & then
        Assertions.assertNotNull() {
            postService.findPostById(postDto)
        }
    }

    @Test
    fun `게시글 전체조회 테스트`() {

        Assertions.assertNotNull() {
            postService.findAllPost();
        }

    }

    @Test
    fun `게시글 검색 테스트`() {
        // given
        val keywordDto = PostKeywordDto(
            keyword = "test"
        )

        // when & then
        Assertions.assertNotNull() {
            postService.findPostByKeyword(keywordDto)
        }
    }

}