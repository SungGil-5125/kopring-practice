package com.project.kopring.domain.user.service

import com.project.kopring.domain.user.presentation.dto.request.SignUpRequest

interface SignUpService {

    fun signUp(signUpRequest: SignUpRequest)

}