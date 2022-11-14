package com.project.kopring.domain.user.entity

import com.project.kopring.domain.user.type.Role
import javax.persistence.*

@Entity
class User(
        val email: String,
        val password: String,
        val name: String,
        var refreshToken: String?,
        @Enumerated(EnumType.STRING)
        @ElementCollection
        @CollectionTable(name = "role", joinColumns = [JoinColumn(name = "userId")])
        val roles: MutableList<Role>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long = 0

    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }
}