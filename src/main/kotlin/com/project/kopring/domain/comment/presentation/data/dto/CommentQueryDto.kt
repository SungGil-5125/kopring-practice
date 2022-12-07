package com.project.kopring.domain.comment.presentation.data.dto

data class CommentQueryDto(
    val id: Long,
    val comment: String,
    val isMine: Boolean,
    val user: UserInfo
) {
    data class UserInfo(
        val id: Long,
        val name: String,
        val imageUrl: String,
    )
}