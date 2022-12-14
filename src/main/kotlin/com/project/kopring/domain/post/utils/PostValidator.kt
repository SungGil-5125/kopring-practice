package com.project.kopring.domain.post.utils

import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.data.type.PostValidatorType

interface PostValidator {

    fun validate(validator: PostValidatorType, postDto: PostDto): Post

}