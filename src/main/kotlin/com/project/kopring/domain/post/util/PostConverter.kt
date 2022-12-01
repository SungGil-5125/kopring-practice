package com.project.kopring.domain.post.util

import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.data.request.PostRequest
import com.project.kopring.domain.post.presentation.data.request.UpdatePostRequest
import com.project.kopring.domain.user.domain.User

interface PostConverter {

    fun toDto(request: PostRequest): PostDto
    fun toDto(id: Long, request: UpdatePostRequest): PostDto
    fun toEntity(dto: PostDto, user: User): Post
//    fun toResponse(id: Long, dto: PostDto): PostResponse

}