package com.project.kopring.domain.user.presentation.dto.request

import javax.validation.constraints.NotBlank

data class ReissueTokenRequest(
        @field:NotBlank
        val refreshToken: String
)