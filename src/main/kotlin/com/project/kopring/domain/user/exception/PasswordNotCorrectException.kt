package com.project.kopring.domain.user.exception

import com.project.kopring.global.exception.ErrorCode

class PasswordNotCorrectException(
        var errorCode: ErrorCode
): RuntimeException(errorCode.message) {
}