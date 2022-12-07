package com.project.kopring.domain.post.presentation.data.dto

import org.springframework.web.multipart.MultipartFile

data class PostDto(
    val id: Long,
    val title: String,
    val content: String,
    val tags: MutableList<String>,
    val file: MultipartFile?
)