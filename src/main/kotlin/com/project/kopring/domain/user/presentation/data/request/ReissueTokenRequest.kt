package com.project.kopring.domain.user.presentation.data.request

import javax.validation.constraints.NotBlank

data class ReissueTokenRequest(
    @field:NotBlank
    val refreshToken: String
)