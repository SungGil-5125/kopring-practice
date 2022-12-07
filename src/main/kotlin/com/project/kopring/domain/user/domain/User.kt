package com.project.kopring.domain.user.domain

import com.project.kopring.domain.user.presentation.data.dto.UserDto
import com.project.kopring.domain.user.type.Role
import javax.persistence.*

@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long,
    val email: String,
    val password: String,
    var name: String,
    var refreshToken: String?,
    var imageUrl: String,
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "role", joinColumns = [JoinColumn(name = "userId")])
    val roles: MutableList<Role>
) {
    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    fun updateUserInfo(userDto: UserDto, imageUrl: String) {
        this.name = userDto.name
        this.imageUrl = "https://devlog-v2-bucket.s3.ap-northeast-2.amazonaws.com/USER/".plus(imageUrl)
    }
}