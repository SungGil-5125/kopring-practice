package com.project.kopring.domain.user.domain

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
    @Column(name = "user_id")
    val userId: Long = 0L

    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }
}