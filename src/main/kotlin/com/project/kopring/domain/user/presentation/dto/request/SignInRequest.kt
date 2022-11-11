package com.project.kopring.domain.user.presentation.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class SignInRequest(
        @Email(message = "이메일 형식이 아닙니다")
        @NotBlank(message = "이메일이 입력되지 않았습니다")
        val email: String,

        @NotBlank(message = "비밀번호가 입력되지 않았습니다")
        val password: String
)