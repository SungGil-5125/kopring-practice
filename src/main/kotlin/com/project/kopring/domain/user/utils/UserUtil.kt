package com.project.kopring.domain.user.utils

import com.project.kopring.domain.user.domain.User
import com.project.kopring.domain.user.presentation.data.dto.UserDto

interface UserUtil {

    fun currentUser(): User
    fun findUserById(userDto: UserDto): User

}