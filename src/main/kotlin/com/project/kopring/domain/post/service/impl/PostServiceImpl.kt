package com.project.kopring.domain.post.service.impl

import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.post.domain.repository.PostRepository
import com.project.kopring.domain.post.exception.PostNotFoundException
import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.data.response.PostListResponse
import com.project.kopring.domain.post.presentation.data.response.PostResponse
import com.project.kopring.domain.post.presentation.data.type.PostValidatorType
import com.project.kopring.domain.post.service.PostService
import com.project.kopring.domain.post.util.PostConverter
import com.project.kopring.domain.post.util.PostValidator
import com.project.kopring.domain.user.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
        private val postRepository: PostRepository,
        private val postConverter: PostConverter,
        private val postValidator: PostValidator,
        private val userUtil: UserUtil
): PostService {

    @Transactional(rollbackFor = [Exception::class])
    override fun writePost(postDto: PostDto) {
        postValidator.validate(PostValidatorType.CREATE, postDto)
                .let { postConverter.toEntity(postDto, userUtil.currentUser()) }
                .let { postRepository.save(it) }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deletePost(postDto: PostDto) {
        postValidator.validate(PostValidatorType.UPDATE, postDto)
                .let { postRepository.deleteById(postDto.id) }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updatePost(postDto: PostDto) {
        postValidator.validate(PostValidatorType.DELETE, postDto)
                .let { postRepository.findById(postDto.id) }
    }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findPostDetailById(postDto: PostDto): PostResponse =
            postRepository.findPostByPostId(postDto.id)
                    .let { it ?: throw PostNotFoundException() }
                    .let { PostResponse(it, isPostMine(it)) }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findAllPost(): PostListResponse {
        return PostListResponse(postRepository.findAll().map { PostResponse(it, isPostMine(it)) })
    }

    private fun isPostMine(post: Post): Boolean {
        return post.user == userUtil.currentUser();
    }



}