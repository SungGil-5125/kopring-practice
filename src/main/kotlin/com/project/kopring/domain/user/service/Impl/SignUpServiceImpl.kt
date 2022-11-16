package com.project.kopring.domain.user.service.Impl

import com.project.kopring.domain.user.exception.DuplicateEmailException
import com.project.kopring.domain.user.facade.UserFacade
import com.project.kopring.domain.user.presentation.dto.request.SignUpRequest
import com.project.kopring.domain.user.service.SignUpService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignUpServiceImpl(
        private val userFacade: UserFacade,
): SignUpService{

    @Transactional(rollbackFor = [Exception::class])
    override fun signUp(signUpRequest: SignUpRequest) {
        if (userFacade.existsByEmail(signUpRequest.email)) {
            throw DuplicateEmailException()
        }
        userFacade.saveUser(signUpRequest)
    }

}