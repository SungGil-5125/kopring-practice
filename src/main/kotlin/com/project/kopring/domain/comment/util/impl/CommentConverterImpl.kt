package com.project.kopring.domain.comment.util.impl

import com.project.kopring.domain.comment.domain.Comment
import com.project.kopring.domain.comment.presentation.data.dto.CommentDto
import com.project.kopring.domain.comment.presentation.data.request.CommentRequest
import com.project.kopring.domain.comment.util.CommentConverter
import com.project.kopring.domain.post.domain.Post
import org.springframework.stereotype.Component

@Component
class CommentConverterImpl: CommentConverter {

    override fun toDto(postId: Long, request: CommentRequest): CommentDto =
        CommentDto(postId, request.comment)

    override fun toEntity(dto: CommentDto, post:Post): Comment =
        Comment(-1, dto.comment, post)

}