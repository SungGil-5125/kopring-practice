package com.project.kopring.domain.post.presentation

import com.project.kopring.domain.post.presentation.dto.request.PostRequest
import com.project.kopring.domain.post.presentation.dto.request.UpdatePostRequest
import com.project.kopring.domain.post.presentation.dto.response.PostListResponse
import com.project.kopring.domain.post.presentation.dto.response.PostResponse
import com.project.kopring.domain.post.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("post")
class PostController(
        private val postService: PostService
) {

    @PostMapping
    fun writePost(@RequestBody request: PostRequest): ResponseEntity<Void> {
        postService.writePost(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("{postId}")
    fun deletePost(@PathVariable postId: Long): ResponseEntity<Void> {
        postService.deletePost(postId)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("{postId}")
    fun updatePost(@PathVariable postId: Long, @RequestBody updatePostRequest: UpdatePostRequest): ResponseEntity<Void> {
        postService.updatePost(postId, updatePostRequest)
        return ResponseEntity.ok().build()
    }

    @GetMapping("{postId}")
    fun findOneById(@PathVariable postId: Long): ResponseEntity<PostResponse> {
        return ResponseEntity.ok(postService.findPostDetailById(postId));
    }

    @GetMapping
    fun findAllPost(): ResponseEntity<PostListResponse> {
        return ResponseEntity.ok(postService.findAllPost())
    }

}