package com.project.kopring.domain.post.presentation.dto.request

class UpdatePostRequest(
        val title: String,
        val content: String,
        val tags: MutableList<String>
)