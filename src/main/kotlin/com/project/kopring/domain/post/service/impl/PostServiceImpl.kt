package com.project.kopring.domain.post.service.impl

import com.project.kopring.domain.comment.domain.repository.CommentRepository
import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.post.domain.repository.PostRepository
import com.project.kopring.domain.post.exception.PostNotFoundException
import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.data.dto.PostKeywordDto
import com.project.kopring.domain.post.presentation.data.dto.PostQueryDto
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
        private val commentRepository: CommentRepository,
        private val userUtil: UserUtil
): PostService {

    @Transactional(rollbackFor = [Exception::class])
    override fun writePost(postDto: PostDto) {
        postConverter.toEntity(postDto, userUtil.currentUser())
                .let { postRepository.save(it) }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updatePost(postDto: PostDto) {
        postValidator.validate(PostValidatorType.UPDATE, postDto)
            .updatePost(postDto)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deletePost(postDto: PostDto) {
        postValidator.validate(PostValidatorType.DELETE, postDto)
            .let { postRepository.deleteById(it.id) }
    }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findPostDetailById(postDto: PostDto): PostQueryDto =
            postRepository.findPostById(postDto.id)
                .let { it ?: throw PostNotFoundException() }
                .let { it -> postConverter.toQueryDto(it, commentRepository.findCommentByPostId(it.id).map { it.comment }.toMutableList(), isPostMine(it, userUtil.currentUser().email)) }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findAllPost(): List<PostQueryDto> {
        return postRepository.findAll()
            .map { it -> postConverter.toQueryDto(it,
                commentRepository.findCommentByPostId(it.id).map { it.comment }.toMutableList(), isPostMine(it, userUtil.currentUser().email)
            ) }
    }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findPostByKeyword(postKeywordDto: PostKeywordDto): List<PostQueryDto> {
        return postRepository.findPostByTitleContaining(postKeywordDto.keyword)
            .map { it -> postConverter.toQueryDto(it,
                commentRepository.findCommentByPostId(it.id).map { it.comment }.toMutableList(), isPostMine(it, userUtil.currentUser().email)
            ) }
    }

    private fun isPostMine(post: Post, email: String): Boolean = post.user.email == email

}