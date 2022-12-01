package com.project.kopring.domain.user.util

import com.project.kopring.domain.user.domain.User

interface UserUtil {

    fun currentUser(): User

}