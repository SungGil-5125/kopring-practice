package com.project.kopring.domain.post.presentation.dto.request

import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.user.domain.User
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

data class PostRequest(
        @field:NotBlank
        val title: String,
        @field:NotBlank
        val content: String,
        @field:NotBlank
        val tags: MutableList<String>
)

fun PostRequest.toEntity(user: User) = Post(
        title = title,
        content = content,
        tags = tags,
        createdDate = LocalDateTime.now(),
        user = user,
)