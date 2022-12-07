package com.project.kopring.domain.comment.utils.impl

import com.project.kopring.domain.comment.domain.Comment
import com.project.kopring.domain.comment.domain.repository.CommentRepository
import com.project.kopring.domain.comment.exception.CommentNotFoundException
import com.project.kopring.domain.comment.presentation.data.dto.CommentDto
import com.project.kopring.domain.comment.utils.CommentValidator
import org.springframework.stereotype.Component

@Component
class CommentValidatorImpl(
    private val commentRepository: CommentRepository
) : CommentValidator {

    override fun validate(commentDto: CommentDto): Comment =
        commentRepository.findCommentById(commentDto.id)
            .let { it ?: throw CommentNotFoundException() }

}