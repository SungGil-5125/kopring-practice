package com.project.kopring.domain.user.util

import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.presentation.data.type.ValidatorType

interface AccountValidator {

    fun validate(validatorType: ValidatorType, dto: UserDto)

}