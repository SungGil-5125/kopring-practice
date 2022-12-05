package com.project.kopring.domain.user.service

import com.project.kopring.domain.user.presentation.data.dto.ReissueTokenDto
import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.presentation.data.response.TokenResponse

interface UserAccountService {

    fun signUp(userDto: UserDto)
    fun signIn(userDto: UserDto): TokenResponse
    fun reissueToken(reissueTokenDto: ReissueTokenDto): TokenResponse

}