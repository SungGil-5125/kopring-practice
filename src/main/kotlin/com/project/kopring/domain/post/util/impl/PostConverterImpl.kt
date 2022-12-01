package com.project.kopring.domain.post.util.impl

import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.data.request.PostRequest
import com.project.kopring.domain.post.presentation.data.request.UpdatePostRequest
import com.project.kopring.domain.post.util.PostConverter
import com.project.kopring.domain.user.domain.User
import org.springframework.stereotype.Component

@Component
class PostConverterImpl: PostConverter {

    override fun toDto(request: PostRequest): PostDto =
            PostDto(-1, request.title, request.content, request.tags)

    override fun toDto(id: Long, request: UpdatePostRequest): PostDto =
            PostDto(id, request.title, request.content, request.tags)


    override fun toEntity(dto: PostDto, user: User): Post =
            Post(dto.title, dto.description, dto.tags, user)

//    override fun toResponse(id: Long, dto: PostDto): PostDto =

}