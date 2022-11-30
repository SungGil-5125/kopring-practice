package com.project.kopring.global.error.exception

import com.project.kopring.global.error.type.ErrorCode

open class BasicException(
        val errorCode: ErrorCode
): RuntimeException(errorCode.message)