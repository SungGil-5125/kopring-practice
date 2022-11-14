package com.project.kopring.domain.user.presentation.controller

import com.project.kopring.domain.user.presentation.dto.*
import com.project.kopring.domain.user.presentation.dto.request.ReissueTokenRequest
import com.project.kopring.domain.user.presentation.dto.request.SignInRequest
import com.project.kopring.domain.user.presentation.dto.request.SignUpRequest
import com.project.kopring.domain.user.presentation.dto.response.NewTokenResponse
import com.project.kopring.domain.user.presentation.dto.response.SignInResponse
import com.project.kopring.domain.user.service.ReissueService
import com.project.kopring.domain.user.service.SignInService
import com.project.kopring.domain.user.service.SignUpService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("auth")
class AuthController(
        private val signUpService: SignUpService,
        private val signInService: SignInService,
        private val reissueService: ReissueService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid signUpRequest: SignUpRequest): ResponseEntity<Void> {
        signUpService.signUp(signUpRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody @Valid signInRequest: SignInRequest): ResponseEntity<SignInResponse> {
        val response = signInService.signIn(signInRequest)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("reissue")
    fun reissueToken(@RequestBody @Valid reissueTokenRequest: ReissueTokenRequest): ResponseEntity<NewTokenResponse> {
        val response = reissueService.reissueToken(reissueTokenRequest)
        return ResponseEntity.ok(response)
    }

}