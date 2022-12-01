package com.project.kopring.domain.post.util.impl

import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.post.domain.repository.PostRepository
import com.project.kopring.domain.post.exception.PostNotFoundException
import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.type.PostValidatorType
import com.project.kopring.domain.post.util.PostValidator
import org.springframework.stereotype.Component

@Component
class PostValidatorImpl(
        private val postRepository: PostRepository
): PostValidator {

    override fun validate(validator: PostValidatorType, postDto: PostDto) {
        when(validator){
            PostValidatorType.CREATE -> postDto
            PostValidatorType.UPDATE -> validatePost(postDto.id)
            PostValidatorType.DELETE -> validatePost(postDto.id)
        }
    }

    private fun validatePost(id: Long): Post {
        return postRepository.findPostByPostId(id)
                ?: throw PostNotFoundException()
    }

}