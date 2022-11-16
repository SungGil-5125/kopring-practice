package com.project.kopring.global.error.exception

import com.project.kopring.global.error.ErrorCode

open class BasicException(
        val errorCode: ErrorCode
): RuntimeException(errorCode.message)