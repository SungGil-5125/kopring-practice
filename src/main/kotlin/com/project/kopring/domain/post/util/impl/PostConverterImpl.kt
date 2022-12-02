package com.project.kopring.domain.post.util.impl

import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.data.dto.PostQueryDto
import com.project.kopring.domain.post.presentation.data.request.PostRequest
import com.project.kopring.domain.post.presentation.data.request.UpdatePostRequest
import com.project.kopring.domain.post.presentation.data.response.PostResponse
import com.project.kopring.domain.post.util.PostConverter
import com.project.kopring.domain.user.domain.User
import org.springframework.stereotype.Component
import java.util.*

@Component
class PostConverterImpl: PostConverter {

    override fun toDto(request: PostRequest): PostDto =
            PostDto(-1, request.title, request.content, request.tags)

    override fun toDto(id: Long, request: UpdatePostRequest): PostDto =
            PostDto(id, request.title, request.content, request.tags)

    override fun toDto(id: Long): PostDto =
        PostDto(id, "", "", Collections.emptyList())

    override fun toEntity(dto: PostDto, user: User): Post =
            Post(-1, dto.title, dto.description, dto.tags, user)

    override fun toQueryDto(post: Post, isMine: Boolean): PostQueryDto =
        PostQueryDto(post.postId, isMine, post.title, post.content, post.tags)

    override fun toResponse(dto: PostQueryDto): PostResponse =
        PostResponse(dto.id, dto.isMine, dto.title, dto.content, dto.tags)

}