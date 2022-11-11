package com.project.kopring.domain.user.presentation.dto.response

import java.time.LocalDateTime

class NewTokenResponse(
        val newAccessToken: String,
        val newRefreshToken: String,
        val expiredAt: LocalDateTime
)