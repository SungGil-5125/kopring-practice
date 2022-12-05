package com.project.kopring.domain.comment.util

import com.project.kopring.domain.comment.domain.Comment
import com.project.kopring.domain.comment.presentation.data.dto.CommentDto

interface CommentValidator {

    fun validate(commentDto: CommentDto): Comment

}