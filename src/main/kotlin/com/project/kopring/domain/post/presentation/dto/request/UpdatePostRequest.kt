package com.project.kopring.domain.post.presentation.dto.request

import javax.validation.constraints.NotBlank

class UpdatePostRequest(
        @field:NotBlank
        val title: String,
        @field:NotBlank
        val content: String,
        @field:NotBlank
        val tags: MutableList<String>
)