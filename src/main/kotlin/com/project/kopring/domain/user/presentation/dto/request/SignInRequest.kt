package com.project.kopring.domain.user.presentation.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class SignInRequest(
        @field:NotBlank
        @field:Email
        val email: String,

        @field:NotBlank
        val password: String
)