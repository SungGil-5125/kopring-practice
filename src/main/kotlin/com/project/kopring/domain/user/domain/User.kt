package com.project.kopring.domain.user.domain

import com.project.kopring.domain.user.type.Role
import javax.persistence.*

@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long,
    val email: String,
    val password: String,
    val name: String,
    var refreshToken: String?,
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "role", joinColumns = [JoinColumn(name = "userId")])
    val roles: MutableList<Role>
) {
    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }
}