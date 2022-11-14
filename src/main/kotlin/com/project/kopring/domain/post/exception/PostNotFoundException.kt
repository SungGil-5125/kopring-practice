package com.project.kopring.domain.post.exception

import com.project.kopring.global.exception.ErrorCode

class PostNotFoundException(
        val errorCode: ErrorCode
): RuntimeException(errorCode.message) {
}