package com.project.kopring.domain.post.presentation.data.request

import javax.validation.constraints.NotBlank

data class PostRequest(
        @field:NotBlank
        val title: String,
        @field:NotBlank
        val content: String,
)
