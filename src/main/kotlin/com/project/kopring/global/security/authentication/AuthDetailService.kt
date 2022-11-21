package com.project.kopring.global.security.authentication

import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.domain.user.facade.UserFacade
import com.project.kopring.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthDetailService(
        private val userRepository: UserRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        AuthDetails(
                userRepository.findByEmail(username) ?: throw UserNotFoundException()
        )
}