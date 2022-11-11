package com.project.kopring.domain.post.presentation.dto.request

import com.project.kopring.domain.post.entity.Post
import com.project.kopring.domain.user.entity.User
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class PostRequest(
        @NotBlank(message = "제목이 입력되지 않았습니다")
        val title: String,
        @NotBlank(message = "주제가 입력되지 않았습니다")
        val content: String,
        @NotNull(message = "태그가 입력되지 않았습니다")
        val tags: MutableList<String>
) {
    fun toEntity(user: User): Post = Post(title, content, createdDate = LocalDateTime.now(), user, tags)
}