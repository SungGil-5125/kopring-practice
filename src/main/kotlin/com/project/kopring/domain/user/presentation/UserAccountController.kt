package com.project.kopring.domain.user.presentation

import com.project.kopring.domain.user.presentation.data.request.SignInRequest
import com.project.kopring.domain.user.presentation.data.request.SignUpRequest
import com.project.kopring.domain.user.presentation.data.response.TokenResponse
import com.project.kopring.domain.user.service.UserAccountService
import com.project.kopring.domain.user.util.AccountConverter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("auth")
class UserAccountController(
    private val userAccountService: UserAccountService,
    private val accountConverter: AccountConverter
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid request: SignUpRequest): ResponseEntity<Void> =
        accountConverter.toDto(request)
            .let { userAccountService.signUp(it) }
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

    @PostMapping("/signin")
    fun signIn(@RequestBody @Valid request: SignInRequest): ResponseEntity<TokenResponse> =
        accountConverter.toDto(request)
            .let { userAccountService.signIn(it) }
            .let { ResponseEntity.ok(it) }

    @PatchMapping("reissue")
    fun reissueToken(@RequestHeader("RefreshToken") refreshToken: String): ResponseEntity<TokenResponse> =
        accountConverter.toDto(refreshToken)
            .let { userAccountService.reissueToken(it) }
            .let { ResponseEntity.ok(it) }

}