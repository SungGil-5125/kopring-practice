package com.project.kopring.domain.user.utils.impl

import com.project.kopring.domain.user.domain.User
import com.project.kopring.domain.user.presentation.data.dto.ReissueTokenDto
import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.presentation.data.dto.UserQueryDto
import com.project.kopring.domain.user.presentation.data.request.SignInRequest
import com.project.kopring.domain.user.presentation.data.request.SignUpRequest
import com.project.kopring.domain.user.presentation.data.request.UpdateUserInfoRequest
import com.project.kopring.domain.user.presentation.data.response.UserInfoResponse
import com.project.kopring.domain.user.type.Role
import com.project.kopring.domain.user.utils.AccountConverter
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class AccountConverterImpl : AccountConverter {

    override fun toDto(request: SignUpRequest) =
        UserDto(-1, request.email, request.password, request.name, null)

    override fun toDto(request: SignInRequest): UserDto =
        UserDto(-1, request.email, request.password, "", null)

    override fun toDto(refreshToken: String): ReissueTokenDto =
        ReissueTokenDto(refreshToken)

    override fun toDto(id: Long): UserDto =
        UserDto(id, "", "", "", null)

    override fun toDto(request: UpdateUserInfoRequest, file: MultipartFile): UserDto =
        UserDto(-1, "", "", request.name, file)

    override fun toEntity(dto: UserDto, encodePassword: String): User =
        User(-1, dto.email, encodePassword, dto.name, "", "", Collections.singletonList(Role.ROLE_USER))

    override fun toQueryDto(user: User): UserQueryDto =
        UserQueryDto(user.userId, user.name, user.imageUrl)

    override fun toResponse(dto: UserQueryDto): UserInfoResponse =
        UserInfoResponse(dto.id, dto.name, dto.image)

}