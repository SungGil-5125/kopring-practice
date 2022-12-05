package com.project.kopring.domain.comment.service

import com.project.kopring.domain.comment.presentation.data.dto.CommentDto

interface CommentService {

    fun writeComment(commentDto: CommentDto)
    fun updateComment(commentDto: CommentDto)
    fun deleteComment(commentDto: CommentDto)

}