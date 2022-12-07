package com.project.kopring.domain.comment.utils

import com.project.kopring.domain.comment.domain.Comment
import com.project.kopring.domain.comment.presentation.data.dto.CommentDto

interface CommentValidator {

    fun validate(commentDto: CommentDto): Comment

}