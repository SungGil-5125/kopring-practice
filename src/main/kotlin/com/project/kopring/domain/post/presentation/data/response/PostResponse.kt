package com.project.kopring.domain.post.presentation.data.response

data class PostResponse(
        val postId: Long,
        val isMine: Boolean,
        val title: String,
        val content: String,
        val comment: MutableList<String>
)