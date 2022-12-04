package com.project.kopring.domain.comment.service.impl

import com.project.kopring.domain.comment.domain.repository.CommentRepository
import com.project.kopring.domain.comment.presentation.data.dto.CommentDto
import com.project.kopring.domain.comment.service.CommentService
import com.project.kopring.domain.comment.util.CommentConverter
import com.project.kopring.domain.post.domain.repository.PostRepository
import com.project.kopring.domain.post.exception.PostNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val commentConverter: CommentConverter
): CommentService {

    @Transactional(rollbackFor = [Exception::class])
    override fun createComment(commentDto: CommentDto) {
        postRepository.findPostById(commentDto.postId)
            .let { it ?: throw PostNotFoundException () }
            .let { commentConverter.toEntity(commentDto, it) }
            .let { commentRepository.save(it) }
    }

}