package com.project.kopring.domain.comment.domain.repository

import com.project.kopring.domain.comment.domain.Comment
import org.springframework.data.repository.CrudRepository

interface CommentRepository: CrudRepository<Comment, Long> {
    fun findCommentByPostId(postId: Long): MutableList<Comment>
}