package com.project.kopring.domain.user.presentation.data.request

import javax.validation.constraints.NotBlank

data class UpdateUserInfoRequest(
    @field:NotBlank
    val name: String,
)