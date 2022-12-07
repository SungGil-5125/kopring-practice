package com.project.kopring.domain.user.utils

import com.project.kopring.domain.user.domain.User
import com.project.kopring.domain.user.presentation.data.dto.ReissueTokenDto
import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.presentation.data.dto.UserQueryDto
import com.project.kopring.domain.user.presentation.data.request.SignInRequest
import com.project.kopring.domain.user.presentation.data.request.SignUpRequest
import com.project.kopring.domain.user.presentation.data.request.UpdateUserInfoRequest
import com.project.kopring.domain.user.presentation.data.response.UserInfoResponse
import org.springframework.web.multipart.MultipartFile

interface AccountConverter {

    fun toDto(request: SignUpRequest): UserDto
    fun toDto(request: SignInRequest): UserDto
    fun toDto(refreshToken: String): ReissueTokenDto
    fun toDto(id: Long): UserDto
    fun toDto(request: UpdateUserInfoRequest, file: MultipartFile): UserDto
    fun toEntity(dto: UserDto, encodePassword: String): User
    fun toQueryDto(user: User): UserQueryDto
    fun toResponse(dto: UserQueryDto): UserInfoResponse

}