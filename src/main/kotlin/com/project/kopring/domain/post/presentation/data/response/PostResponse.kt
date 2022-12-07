package com.project.kopring.domain.post.presentation.data.response

import com.project.kopring.domain.comment.presentation.data.dto.CommentQueryDto

data class PostResponse(
        val postId: Long,
        val isMine: Boolean,
        val title: String,
        val content: String,
        val tags: MutableList<String>,
        val imageUrl: String,
        val comments: MutableList<CommentQueryDto>
)