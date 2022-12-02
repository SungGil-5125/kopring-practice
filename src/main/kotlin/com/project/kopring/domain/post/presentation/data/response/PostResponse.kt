package com.project.kopring.domain.post.presentation.data.response

data class PostResponse(
        val postId: Long,
        var isMine: Boolean,
        val title: String,
        val content: String,
        val tags: MutableList<String>
)