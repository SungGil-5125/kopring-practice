package com.project.kopring.domain.user.utils.impl

import com.project.kopring.domain.user.domain.User
import com.project.kopring.domain.user.domain.repository.UserRepository
import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.utils.UserUtil
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class UserUtilImpl(
    private val userRepository: UserRepository
) : UserUtil {

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun currentUser(): User {
        val id = SecurityContextHolder.getContext().authentication.name
        return userRepository.findByEmail(id)
            ?: throw UserNotFoundException()
    }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findUserById(userDto: UserDto): User =
        userRepository.findById(userDto.userId).orElseThrow() { throw UserNotFoundException() }

}