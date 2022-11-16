package com.project.kopring.global.security.authentication

import com.project.kopring.domain.user.facade.UserFacade
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthDetailService(
        private val userFacade: UserFacade
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        AuthDetails(userFacade.findUserByEmail(username))
}