package com.project.kopring.domain.user.exception.handler

import com.project.kopring.domain.user.exception.DuplicateEmailException
import com.project.kopring.domain.user.exception.PasswordNotCorrectException
import com.project.kopring.domain.user.exception.RefreshTokenExpiredException
import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.global.exception.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun userNotFoundException(request: HttpServletRequest, e: UserNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(e.errorCode.status, e.errorCode.message)
        return ResponseEntity(errorResponse, HttpStatus.valueOf(e.errorCode.status))
    }

    @ExceptionHandler(DuplicateEmailException::class)
    fun duplicateEmailException(request: HttpServletRequest, e: DuplicateEmailException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(e.errorCode.status, e.errorCode.message)
        return ResponseEntity(errorResponse, HttpStatus.valueOf(e.errorCode.status))
    }

    @ExceptionHandler(PasswordNotCorrectException::class)
    fun passwordNotCorrectException(request: HttpServletRequest, e: PasswordNotCorrectException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(e.errorCode.status, e.errorCode.message)
        return ResponseEntity(errorResponse, HttpStatus.valueOf(e.errorCode.status))
    }

    @ExceptionHandler(RefreshTokenExpiredException::class)
    fun refreshTokenExpiredException(request: HttpServletRequest, e: RefreshTokenExpiredException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(e.errorCode.status, e.errorCode.message)
        return ResponseEntity(errorResponse, HttpStatus.valueOf(e.errorCode.status))
    }
}