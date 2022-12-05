package com.project.kopring.domain.post.presentation.data.dto

import com.project.kopring.domain.comment.presentation.data.dto.CommentQueryDto

data class PostQueryDto(
    val id: Long,
    val isMine: Boolean,
    val title: String,
    val content: String,
    val tags: MutableList<String>,
    val comments: MutableList<CommentQueryDto>
)