package com.project.kopring.domain.comment.presentation.data.dto

data class CommentQueryDto(
    val id: Long,
    val isMine: Boolean,
    val comment: String,
    val name: String,
)