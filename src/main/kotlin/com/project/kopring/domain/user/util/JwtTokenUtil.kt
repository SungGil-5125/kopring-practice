package com.project.kopring.domain.user.util

import com.project.kopring.domain.user.presentation.data.response.TokenResponse

interface JwtTokenUtil {

    fun generateJwtToken(email: String): TokenResponse

}