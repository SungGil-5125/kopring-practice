package com.project.kopring.domain.user.util.impl

import com.project.kopring.domain.user.domain.User
import com.project.kopring.domain.user.domain.repository.UserRepository
import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.domain.user.util.UserUtil
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserUtilImpl(
        private val userRepository: UserRepository
): UserUtil {

    override fun currentUser(): User {
        val email = SecurityContextHolder.getContext().authentication.name
        return userRepository.findByEmail(email)
                ?: throw UserNotFoundException()
    }

}