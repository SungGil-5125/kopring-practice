package com.project.kopring.infrastructure.s3.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(value = "cloud.aws.s3")
data class S3BucketProperties(
    var bucket: String
)