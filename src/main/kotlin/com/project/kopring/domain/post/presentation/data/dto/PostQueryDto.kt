package com.project.kopring.domain.post.presentation.data.dto

class PostQueryDto(
    val id: Long,
    val isMine: Boolean,
    val title: String,
    val content: String,
    val tags: MutableList<String>
)