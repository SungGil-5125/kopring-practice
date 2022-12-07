package com.project.kopring.domain.comment.utils

import com.project.kopring.domain.comment.domain.Comment
import com.project.kopring.domain.comment.presentation.data.dto.CommentDto
import com.project.kopring.domain.comment.presentation.data.request.CommentRequest
import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.user.domain.User

interface CommentConverter {

    fun toDto(id: Long, request: CommentRequest): CommentDto
    fun toDto(id: Long): CommentDto
    fun toEntity(dto: CommentDto, post: Post, user: User): Comment

}