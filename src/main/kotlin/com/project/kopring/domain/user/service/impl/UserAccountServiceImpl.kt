package com.project.kopring.domain.user.service.impl

import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.service.UserAccountService
import com.project.kopring.domain.user.utils.AccountConverter
import com.project.kopring.domain.user.utils.UserUtil
import com.project.kopring.infrastructure.s3.service.S3Service
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAccountServiceImpl(
    private val userUtil: UserUtil,
    private val accountConverter: AccountConverter,
    private val s3Service: S3Service
): UserAccountService {

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findUserById(userDto: UserDto) =
        userUtil.findUserById(userDto)
            .let { accountConverter.toQueryDto(it) }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateUserInfo(userDto: UserDto) {
        val uploadFile = userDto.file?.let { s3Service.uploadFile(it, "USER/") }
        userUtil.currentUser()
            .let {
                if(uploadFile != null) it.updateUserInfo(userDto, uploadFile)
                else it.updateUserInfo(userDto, it.imageUrl)
            }
    }

}