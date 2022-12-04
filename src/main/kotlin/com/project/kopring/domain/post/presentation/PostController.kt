package com.project.kopring.domain.post.presentation

import com.project.kopring.domain.post.presentation.data.request.PostRequest
import com.project.kopring.domain.post.presentation.data.request.UpdatePostRequest
import com.project.kopring.domain.post.presentation.data.response.PostResponse
import com.project.kopring.domain.post.service.PostService
import com.project.kopring.domain.post.util.PostConverter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("post")
class PostController(
        private val postService: PostService,
        private val postConverter: PostConverter
) {

    @PostMapping
    fun writePost(@RequestBody request: PostRequest): ResponseEntity<Void> =
        postConverter.toDto(request)
            .let { postService.writePost(it) }
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }


    @DeleteMapping("{id}")
    fun deletePost(@PathVariable id: Long): ResponseEntity<Void> =
        postConverter.toDto(id)
            .let { postService.deletePost(it) }
            .let { ResponseEntity.ok().build() }

    @PatchMapping("{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody updatePostRequest: UpdatePostRequest): ResponseEntity<Void> =
        postConverter.toDto(id, updatePostRequest)
            .let { postService.updatePost(it) }
            .let { ResponseEntity.ok().build() }

    @GetMapping("{id}")
    fun findOneById(@PathVariable id: Long): ResponseEntity<PostResponse> =
        postConverter.toDto(id)
            .let { postService.findPostDetailById(it) }
            .let { postConverter.toResponse(it) }
            .let { ResponseEntity.ok(it) }

    @GetMapping
    fun findAllPost(): ResponseEntity<List<PostResponse>> =
        postService.findAllPost()
            .map { postConverter.toResponse(it) }
            .let { ResponseEntity.ok(it) }

    @GetMapping("search")
    fun findPostByKeyword(@RequestParam("keyword") keyword: String) =
        postConverter.toDto(keyword)
            .let { postService.findPostByKeyword(it) }
            .let { ResponseEntity.ok(it) }

}