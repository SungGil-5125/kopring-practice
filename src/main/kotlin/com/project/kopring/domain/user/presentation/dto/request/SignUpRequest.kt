package com.project.kopring.domain.user.presentation.dto.request

import com.project.kopring.domain.user.entity.User
import com.project.kopring.domain.user.type.Role
import java.util.Collections
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
) {

    fun toEntity(password: String): User = User(email = email, password = password, name = name, roles = Collections.singletonList(Role.ROLE_USER), refreshToken = null)

}