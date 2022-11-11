package com.project.kopring.global.security.filter

import com.project.kopring.global.security.authentication.AuthDetailService
import com.project.kopring.global.security.jwt.JwtTokenProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter(
        private val jwtTokenProvider: JwtTokenProvider,
        private val authDetailService: AuthDetailService
): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val accessToken: String? = request.getHeader("Authorization")

        if(accessToken != null) {
            val email: String = jwtTokenProvider.getUserEmail(accessToken);
            registerUserInfoSecurityContext(email, request)
        }

        filterChain.doFilter(request, response)
    }

    fun registerUserInfoSecurityContext(email: String, request: HttpServletRequest) {
        try {
            val userDetails: UserDetails = authDetailService.loadUserByUsername(email)
            val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, null)
            usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = (usernamePasswordAuthenticationToken)
        } catch (e: NullPointerException) {
            throw RuntimeException()
        }
    }
}