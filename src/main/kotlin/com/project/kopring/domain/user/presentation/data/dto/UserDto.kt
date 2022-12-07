package com.project.kopring.domain.user.presentation.data.dto

import org.springframework.web.multipart.MultipartFile

data class UserDto(
    val userId: Long,
    val email: String,
    val password: String,
    val name: String,
    val file: MultipartFile?
)