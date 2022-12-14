package com.project.kopring.domain.post.presentation.data.dto

import com.project.kopring.domain.comment.presentation.data.dto.CommentQueryDto

data class PostQueryDto(
    val postId: Long,
    val userId: Long,
    val isMine: Boolean,
    val title: String,
    val content: String,
    val tags: MutableList<String>,
    val imageUrl: String,
    val comments: MutableList<CommentQueryDto>
)