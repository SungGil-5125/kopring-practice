package com.project.kopring.domain.post.presentation.data.dto

data class PostQueryDto(
    val id: Long,
    val isMine: Boolean,
    val title: String,
    val content: String,
    val comment: MutableList<String>
)