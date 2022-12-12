package com.project.kopring.domain.comment.service

import com.project.kopring.domain.comment.domain.repository.CommentRepository
import com.project.kopring.domain.comment.exception.CommentNotFoundException
import com.project.kopring.domain.comment.presentation.data.dto.CommentDto
import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.service.UserAuthService
import com.project.kopring.global.security.authentication.AuthDetailService
import com.project.kopring.global.security.jwt.JwtTokenProvider
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

@SpringBootTest
class CommentServiceTest(
    @Autowired
    private val commentService: CommentService,
    @Autowired
    private val commentRepository: CommentRepository,
    @Autowired
    private val userAuthService: UserAuthService,
    @Autowired
    private val authDetailService: AuthDetailService,
    @Autowired
    private val jwtTokenProvider: JwtTokenProvider
) {

    private val email = "test@test.com"

    private val password = "test password"

    private val name = "test name"

    private val comment = "test comment"

    @BeforeEach
    fun initUser() {
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
    }

    @Test
    fun `댓글 생성 테스트`() {
        // given
        val commentDto = CommentDto(
            id = 5,
            comment = comment
        )

        // when
        commentService.writeComment(commentDto)

        // then
        Assertions.assertNotNull {
            commentRepository.findCommentById(1)
        }
    }

    @Test
    fun `댓글 수정 테스트`() {
        // given
        val commentDto = CommentDto(
            id = 1,
            comment = comment
        )

        // when
        commentService.updateComment(commentDto)

        // then
        Assertions.assertNotNull {
            commentRepository.findCommentById(1)
        }
    }

    @Test
    fun `댓글 삭제 테스트`() {
        // given
        val commentDto = CommentDto(
            id = 2,
            comment = ""
        )

        // when & then
        assertThrows<CommentNotFoundException> {
            commentService.deleteComment(commentDto)
        }
    }

    @Test
    fun `댓글이 없을 경우`() {
        // given
        val commentDto = CommentDto(
            id = 1,
            comment = ""
        )

        // when & then
        assertThrows<CommentNotFoundException> {
            commentService.updateComment(commentDto)
        }
    }

}