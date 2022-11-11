package com.project.kopring.global.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.global.exception.ErrorCode
import com.project.kopring.global.exception.ErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ExceptionHandlerFilter(
        private val objectMapper: ObjectMapper
): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        try {
            filterChain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            setErrorResponse(ErrorCode.EXPIRATION_TOKEN, response)
        } catch (e: JwtException) {
            setErrorResponse(ErrorCode.INVALID_TOKEN, response)
        } catch (e: UserNotFoundException) {
            setErrorResponse(ErrorCode.USER_NOT_FOUND, response)
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