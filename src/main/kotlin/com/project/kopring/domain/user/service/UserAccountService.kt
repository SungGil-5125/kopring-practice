package com.project.kopring.domain.user.service

import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.presentation.data.dto.UserQueryDto

interface UserAccountService {

    fun findUserById(userDto: UserDto): UserQueryDto
    fun updateUserInfo(userDto: UserDto)

}