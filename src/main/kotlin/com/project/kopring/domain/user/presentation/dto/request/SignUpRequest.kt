package com.project.kopring.domain.user.presentation.dto.request

import com.project.kopring.domain.user.domain.User
import com.project.kopring.domain.user.type.Role
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class SignUpRequest(
        @field:NotBlank
        @field:Email
        val email: String,

        @field:NotBlank
        val password: String,

        @field:NotBlank
        val name: String
)

fun SignUpRequest.toEntity(password: String) = User(
        email = email,
        password = password,
        name = name,
        roles = Collections.singletonList(Role.ROLE_USER),
        refreshToken = null
)