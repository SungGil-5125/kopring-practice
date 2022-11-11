package com.project.kopring.domain.user.presentation.dto.request

import com.project.kopring.domain.user.entity.User
import com.project.kopring.domain.user.type.Role
import java.util.Collections
import javax.validation.constraints.NotBlank

data class SignUpRequest(
        @NotBlank(message = "아이디를 입력해주세요")
        val email: String,

        @NotBlank(message = "비밀번호를 입력해주세요")
        val password: String,

        @NotBlank(message = "이름을 입력해주세요")
        val name: String
) {

    fun toEntity(password: String): User = User(email = email, password = password, name = name, roles = Collections.singletonList(Role.ROLE_USER), refreshToken = null)

}