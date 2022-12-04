package com.project.kopring.domain.user.util.impl

import com.project.kopring.domain.user.domain.User
import com.project.kopring.domain.user.presentation.data.dto.ReissueTokenDto
import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.presentation.data.request.ReissueTokenRequest
import com.project.kopring.domain.user.presentation.data.request.SignInRequest
import com.project.kopring.domain.user.presentation.data.request.SignUpRequest
import com.project.kopring.domain.user.presentation.data.response.UserInfoResponse
import com.project.kopring.domain.user.type.Role
import com.project.kopring.domain.user.util.AccountConverter
import org.springframework.stereotype.Component
import java.util.*

@Component
class AccountConverterImpl: AccountConverter {

    override fun toDto(request: SignUpRequest) =
            UserDto(request.email, request.password, request.name)

    override fun toDto(request: SignInRequest): UserDto =
            UserDto(request.email, request.password, "")

    override fun toDto(request: ReissueTokenRequest): ReissueTokenDto =
            ReissueTokenDto(request.refreshToken)

    override fun toEntity(dto: UserDto, encodePassword: String): User =
            User(-1, dto.email, encodePassword, dto.name, "", Collections.singletonList(Role.ROLE_USER))

    override fun toResponse(dto: UserDto): UserInfoResponse =
            UserInfoResponse(dto.email, dto.password, dto.name)

}