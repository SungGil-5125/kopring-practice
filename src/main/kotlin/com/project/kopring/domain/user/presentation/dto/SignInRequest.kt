package com.project.kopring.domain.user.presentation.dto

import javax.validation.constraints.NotBlank

data class SignInRequest(
        @NotBlank(message = "이메일이 입력되지 않았습니다")
        val email: String,
        @NotBlank(message = "비밀번호가 입력되지 않았습니다")
        val password: String
)