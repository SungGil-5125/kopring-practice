package com.project.kopring.domain.post.utils

import com.project.kopring.domain.comment.presentation.data.dto.CommentQueryDto
import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.data.dto.PostKeywordDto
import com.project.kopring.domain.post.presentation.data.dto.PostListQueryDto
import com.project.kopring.domain.post.presentation.data.dto.PostQueryDto
import com.project.kopring.domain.post.presentation.data.request.PostRequest
import com.project.kopring.domain.post.presentation.data.request.UpdatePostRequest
import com.project.kopring.domain.post.presentation.data.response.PostListResponse
import com.project.kopring.domain.post.presentation.data.response.PostResponse
import com.project.kopring.domain.user.domain.User
import org.springframework.web.multipart.MultipartFile

interface PostConverter {

    fun toDto(request: PostRequest, file: MultipartFile): PostDto
    fun toDto(id: Long, request: UpdatePostRequest, file: MultipartFile): PostDto
    fun toDto(id: Long): PostDto
    fun toDto(keyword: String): PostKeywordDto
    fun toEntity(dto: PostDto, user: User, uploadFile: String): Post
    fun toQueryDto(post: Post, comment: MutableList<CommentQueryDto>, isMine: Boolean): PostQueryDto
    fun toResponse(dto: PostQueryDto): PostResponse
    fun toListResponse(dto: PostListQueryDto): PostListResponse

}