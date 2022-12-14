package com.project.kopring.global.security.jwt.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(value = "jwt.secret")
class JwtProperties(
    var key: String
)