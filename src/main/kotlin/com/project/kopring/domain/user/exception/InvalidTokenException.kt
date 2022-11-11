package com.project.kopring.domain.user.exception

import com.project.kopring.global.exception.ErrorCode

class InvalidTokenException(
        val errorCode: ErrorCode
): RuntimeException(errorCode.message)