package com.project.kopring.domain.comment.service.impl

import com.project.kopring.domain.comment.domain.repository.CommentRepository
import com.project.kopring.domain.comment.presentation.data.dto.CommentDto
import com.project.kopring.domain.comment.service.CommentService
import com.project.kopring.domain.comment.utils.CommentConverter
import com.project.kopring.domain.comment.utils.CommentValidator
import com.project.kopring.domain.post.domain.repository.PostRepository
import com.project.kopring.domain.post.exception.PostNotFoundException
import com.project.kopring.domain.user.utils.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val commentConverter: CommentConverter,
    private val commentValidator: CommentValidator,
    private val postRepository: PostRepository,
    private val userUtil: UserUtil,
) : CommentService {

    @Transactional(rollbackFor = [Exception::class])
    override fun writeComment(commentDto: CommentDto) {
        postRepository.findPostById(commentDto.id)
            .let { it ?: throw PostNotFoundException() }
            .let { commentConverter.toEntity(commentDto, it, userUtil.currentUser()) }
            .let { commentRepository.save(it) }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateComment(commentDto: CommentDto) {
        commentValidator.validate(commentDto)
            .updateComment(commentDto.comment)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteComment(commentDto: CommentDto) {
        commentValidator.validate(commentDto)
            .let { commentRepository.delete(it) }
    }

}