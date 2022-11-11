package com.project.kopring.domain.user.service

import com.project.kopring.domain.user.presentation.dto.request.SignInRequest
import com.project.kopring.domain.user.presentation.dto.response.SignInResponse

interface SignInService {

    fun signIn(signInRequest: SignInRequest): SignInResponse

}