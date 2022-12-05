package com.project.kopring.domain.comment.util.impl

import com.project.kopring.domain.comment.domain.Comment
import com.project.kopring.domain.comment.presentation.data.dto.CommentDto
import com.project.kopring.domain.comment.presentation.data.request.CommentRequest
import com.project.kopring.domain.comment.util.CommentConverter
import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.user.domain.User
import org.springframework.stereotype.Component

@Component
class CommentConverterImpl: CommentConverter {

    override fun toDto(id: Long, request: CommentRequest): CommentDto =
        CommentDto(id, request.comment)

    override fun toDto(id: Long): CommentDto =
        CommentDto(id, "")

    override fun toEntity(dto: CommentDto, post: Post, user: User): Comment =
        Comment(-1, dto.comment, post, user)

}