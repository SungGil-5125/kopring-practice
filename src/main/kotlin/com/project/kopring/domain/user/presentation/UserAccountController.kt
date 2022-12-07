package com.project.kopring.domain.user.presentation

import com.project.kopring.domain.user.presentation.data.request.UpdateUserInfoRequest
import com.project.kopring.domain.user.presentation.data.response.UserInfoResponse
import com.project.kopring.domain.user.service.UserAccountService
import com.project.kopring.domain.user.utils.AccountConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("user")
class UserAccountController(
    private val userAccountService: UserAccountService,
    private val accountConverter: AccountConverter
) {

    @GetMapping("{id}")
    fun findUserById(@PathVariable id: Long): ResponseEntity<UserInfoResponse> =
        accountConverter.toDto(id)
            .let { userAccountService.findUserById(it) }
            .let { accountConverter.toResponse(it) }
            .let { ResponseEntity.ok(it) }

    @PatchMapping
    fun updateUserInfo(@RequestPart request: UpdateUserInfoRequest, @RequestPart file: MultipartFile) =
        accountConverter.toDto(request, file)
            .let { userAccountService.updateUserInfo(it) }

}