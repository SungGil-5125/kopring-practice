package com.project.kopring.global.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.global.error.type.ErrorCode
import com.project.kopring.global.error.response.ErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        runCatching {
            filterChain.doFilter(request, response)
        }.onFailure { throwable ->
            when (throwable) {
                is ExpiredJwtException -> setErrorResponse(ErrorCode.EXPIRATION_TOKEN, response)
                is JwtException -> setErrorResponse(ErrorCode.INVALID_TOKEN, response)
                is UserNotFoundException -> setErrorResponse(ErrorCode.USER_NOT_FOUND, response)
                else -> setErrorResponse(ErrorCode.INTERVAL_SERVER_ERROR, response)
            }
        }
    }

    fun setErrorResponse(errorCode: ErrorCode, response: HttpServletResponse) {
        response.status = errorCode.status
        response.contentType = "application/json; charset=utf-8"
        val errorResponse = ErrorResponse(errorCode.status, errorCode.message)
        val errorResponseEntityToJson = objectMapper.writeValueAsString(errorResponse)
        response.writer.write(errorResponseEntityToJson)
    }
}