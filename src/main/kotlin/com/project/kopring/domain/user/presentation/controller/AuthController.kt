package com.project.kopring.domain.user.presentation.controller

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
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Void> {
        signUpService.signUp(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody request: SignInRequest): ResponseEntity<SignInResponse> {
        return ResponseEntity.ok(signInService.signIn(request))
    }

    @PatchMapping("reissue")
    fun reissueToken(@RequestBody request: ReissueTokenRequest): ResponseEntity<NewTokenResponse> {
        return ResponseEntity.ok(reissueService.reissueToken(request))
    }

}