package com.project.kopring.domain.user.entity

import com.project.kopring.domain.user.type.Role
import javax.persistence.*

@Entity
class User(
        var email: String,
        var password: String,
        var name: String,
        val refreshToken: String? = null,
        @Enumerated(EnumType.STRING)
        @ElementCollection
        @CollectionTable(name = "role", joinColumns = [JoinColumn(name = "userId")])
        val roles: MutableList<Role>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long = 0
}