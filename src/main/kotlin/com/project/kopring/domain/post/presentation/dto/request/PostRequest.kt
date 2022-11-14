package com.project.kopring.domain.post.presentation.dto.request

import com.project.kopring.domain.post.entity.Post
import com.project.kopring.domain.user.entity.User
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class PostRequest(
        @field:NotBlank
        val title: String,
        @field:NotBlank
        val content: String,
        @field:NotBlank
        val tags: MutableList<String>
) {
    fun toEntity(user: User): Post = Post(title, content, createdDate = LocalDateTime.now(), user, tags)
}