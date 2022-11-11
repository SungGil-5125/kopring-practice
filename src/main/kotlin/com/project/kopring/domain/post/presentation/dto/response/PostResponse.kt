package com.project.kopring.domain.post.presentation.dto.response

import com.project.kopring.domain.post.entity.Post

data class PostResponse(
        val postId: Long,
        val title: String,
        val content: String,
        val tags: MutableList<String>
) {
    constructor(post: Post): this (
            post.postId,
            post.title,
            post.content,
            post.tags
    )
}