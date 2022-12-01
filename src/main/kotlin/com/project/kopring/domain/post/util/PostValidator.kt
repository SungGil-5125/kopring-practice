package com.project.kopring.domain.post.util

import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.type.PostValidatorType

interface PostValidator {

    fun validate(validator: PostValidatorType, postDto: PostDto)

}