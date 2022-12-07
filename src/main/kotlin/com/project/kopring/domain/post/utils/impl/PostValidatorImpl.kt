package com.project.kopring.domain.post.utils.impl

import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.post.domain.repository.PostRepository
import com.project.kopring.domain.post.exception.PostNotFoundException
import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.data.type.PostValidatorType
import com.project.kopring.domain.post.utils.PostValidator
import org.springframework.stereotype.Component

@Component
class PostValidatorImpl(
    private val postRepository: PostRepository
) : PostValidator {

    override fun validate(validator: PostValidatorType, postDto: PostDto): Post =
        when (validator) {
            PostValidatorType.UPDATE -> validatePost(postDto.id)
            PostValidatorType.DELETE -> validatePost(postDto.id)
        }

    private fun validatePost(id: Long): Post =
        postRepository.findPostById(id)
            ?: throw PostNotFoundException()


}