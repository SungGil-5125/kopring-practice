package com.project.kopring.domain.comment.presentation

import com.project.kopring.domain.comment.presentation.data.request.CommentRequest
import com.project.kopring.domain.comment.service.CommentService
import com.project.kopring.domain.comment.util.CommentConverter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("comment")
class CommentController(
    private val commentService: CommentService,
    private val commentConverter: CommentConverter
) {

    @PostMapping("{postId}")
    fun writeComment(@PathVariable postId: Long, @RequestBody request: CommentRequest): ResponseEntity<Void> =
        commentConverter.toDto(postId, request)
            .let { commentService.createComment(it) }
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

}