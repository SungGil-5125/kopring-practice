package com.project.kopring.domain.user.util.impl

import com.project.kopring.domain.user.domain.repository.UserRepository
import com.project.kopring.domain.user.exception.DuplicateEmailException
import com.project.kopring.domain.user.exception.PasswordNotCorrectException
import com.project.kopring.domain.user.exception.UserNotFoundException
import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.presentation.data.type.ValidatorType
import com.project.kopring.domain.user.util.AccountValidator
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AccountValidatorImpl(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
): AccountValidator{

    override fun validate(validatorType: ValidatorType, dto: UserDto) {
        when(validatorType) {
            ValidatorType.SIGNUP -> validatorSignUpEmail(dto.email)
            ValidatorType.SIGNIN -> validatorSignInInfo(dto.email, dto.password)
        }
    }

    private fun validatorSignUpEmail(email: String) {
        if(userRepository.existsByEmail(email)) {
            throw DuplicateEmailException()
        }
    }

    private fun validatorSignInInfo(email: String, password: String) {
        userRepository.findByEmail(email).let {
            it ?: throw UserNotFoundException()
        }.let {
            passwordEncoder.matches(password, it.password)
        }.let {
            if(it) return
            else throw PasswordNotCorrectException()
        }
    }

}