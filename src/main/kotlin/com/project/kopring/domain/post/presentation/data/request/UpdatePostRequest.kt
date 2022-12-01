package com.project.kopring.domain.post.presentation.data.request

import javax.validation.constraints.NotBlank

class UpdatePostRequest(
        @field:NotBlank
        val title: String,
        @field:NotBlank
        val content: String,
        @field:NotBlank
        val tags: MutableList<String>
)