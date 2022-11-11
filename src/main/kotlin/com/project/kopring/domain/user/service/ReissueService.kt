package com.project.kopring.domain.user.service

import com.project.kopring.domain.user.presentation.dto.request.ReissueTokenRequest
import com.project.kopring.domain.user.presentation.dto.response.NewTokenResponse

interface ReissueService {

    fun reissueToken(reissueTokenRequest: ReissueTokenRequest): NewTokenResponse

}