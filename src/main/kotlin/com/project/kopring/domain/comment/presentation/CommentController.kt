package com.project.kopring.domain.comment.presentation

import com.project.kopring.domain.comment.presentation.data.request.CommentRequest
import com.project.kopring.domain.comment.service.CommentService
import com.project.kopring.domain.comment.utils.CommentConverter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("comment")
class CommentController(
    private val commentService: CommentService,
    private val commentConverter: CommentConverter
) {

    @PostMapping("{postId}")
    fun writeComment(@PathVariable postId: Long, @RequestBody request: CommentRequest): ResponseEntity<Void> =
        commentConverter.toDto(postId, request)
            .let { commentService.writeComment(it) }
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

    @PatchMapping("{commentId}")
    fun updateComment(@PathVariable commentId: Long, @RequestBody request: CommentRequest): ResponseEntity<Void> =
        commentConverter.toDto(commentId, request)
            .let { commentService.updateComment(it) }
            .let { ResponseEntity.status(HttpStatus.OK).build() }

    @DeleteMapping("{commentId}")
    fun updateComment(@PathVariable commentId: Long): ResponseEntity<Void> =
        commentConverter.toDto(commentId)
            .let { commentService.deleteComment(it) }
            .let { ResponseEntity.status(HttpStatus.OK).build() }

}