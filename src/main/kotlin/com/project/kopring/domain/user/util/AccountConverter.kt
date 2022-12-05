package com.project.kopring.domain.user.util

import com.project.kopring.domain.user.domain.User
import com.project.kopring.domain.user.presentation.data.dto.ReissueTokenDto
import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.presentation.data.request.SignInRequest
import com.project.kopring.domain.user.presentation.data.request.SignUpRequest
import com.project.kopring.domain.user.presentation.data.response.UserInfoResponse

interface AccountConverter {

    fun toDto(request: SignUpRequest): UserDto
    fun toDto(request: SignInRequest): UserDto
    fun toDto(refreshToken: String): ReissueTokenDto
    fun toEntity(dto: UserDto, encodePassword: String): User
    fun toResponse(dto: UserDto): UserInfoResponse

}