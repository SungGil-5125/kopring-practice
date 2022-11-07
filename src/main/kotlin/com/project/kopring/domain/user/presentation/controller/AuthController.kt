package com.project.kopring.domain.user.presentation.controller

import com.project.kopring.domain.user.presentation.dto.SignInRequest
import com.project.kopring.domain.user.presentation.dto.SignUpRequest
import com.project.kopring.domain.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("auth")
class AuthController(
        private val userService: UserService
) {

    @PostMapping("/signup")
    fun signUp(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<Void> {
        userService.signUp(signUpRequest)
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    fun signIn(@Valid @RequestBody signInRequest: SignInRequest): ResponseEntity<Void> {
        userService.signIn(signInRequest)
        return ResponseEntity.ok().build()
    }
}