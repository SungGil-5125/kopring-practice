package com.project.kopring.global.error.handler

import com.project.kopring.global.error.exception.BasicException
import com.project.kopring.global.error.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BasicException::class)
    fun globalExceptionHandler(e: BasicException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(e.errorCode.status, e.errorCode.message), HttpStatus.valueOf(e.errorCode.status))
    }

}