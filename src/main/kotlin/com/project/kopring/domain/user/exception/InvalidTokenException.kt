package com.project.kopring.domain.user.exception

import com.project.kopring.global.error.ErrorCode
import com.project.kopring.global.error.exception.BasicException

class InvalidTokenException: BasicException(ErrorCode.INVALID_TOKEN)