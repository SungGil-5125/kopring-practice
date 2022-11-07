package com.project.kopring.domain.user.exception

import com.project.kopring.global.exception.ErrorCode

open class PasswordNotCorrectException(errorCode: ErrorCode): RuntimeException(errorCode.message) {
}