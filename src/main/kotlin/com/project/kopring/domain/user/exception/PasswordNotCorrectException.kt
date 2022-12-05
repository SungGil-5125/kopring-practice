package com.project.kopring.domain.user.exception

import com.project.kopring.global.error.exception.BasicException
import com.project.kopring.global.error.type.ErrorCode

class PasswordNotCorrectException: BasicException(ErrorCode.PASSWORD_NOT_CORRECT)
