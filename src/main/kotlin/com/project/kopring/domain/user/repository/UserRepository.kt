package com.project.kopring.domain.user.repository

import com.project.kopring.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {

    fun findByEmail(email: String?): User?

    fun existsByEmail(email: String): Boolean

}