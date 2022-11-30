package com.project.kopring.domain.post.presentation.dto.response

import com.project.kopring.domain.post.domain.Post

data class PostResponse(
        val postId: Long,
        var isMine: Boolean,
        val title: String,
        val content: String,
        val tags: MutableList<String>
) {
    constructor(post: Post, isMine: Boolean): this (
        post.postId,
        isMine,
        post.title,
        post.content,
        post.tags
    )
}