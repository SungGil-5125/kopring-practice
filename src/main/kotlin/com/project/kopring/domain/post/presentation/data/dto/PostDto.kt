package com.project.kopring.domain.post.presentation.data.dto

data class PostDto (
        val id: Long,
        val title: String,
        val content: String,
        val tags: MutableList<String>
)