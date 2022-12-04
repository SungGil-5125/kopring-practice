package com.project.kopring.domain.post.util.impl

import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.data.dto.PostKeywordDto
import com.project.kopring.domain.post.presentation.data.dto.PostQueryDto
import com.project.kopring.domain.post.presentation.data.request.PostRequest
import com.project.kopring.domain.post.presentation.data.request.UpdatePostRequest
import com.project.kopring.domain.post.presentation.data.response.PostResponse
import com.project.kopring.domain.post.util.PostConverter
import com.project.kopring.domain.user.domain.User
import org.springframework.stereotype.Component

@Component
class PostConverterImpl: PostConverter {

    override fun toDto(request: PostRequest): PostDto =
            PostDto(-1, request.title, request.content)

    override fun toDto(id: Long, request: UpdatePostRequest): PostDto =
            PostDto(id, request.title, request.content)

    override fun toDto(id: Long): PostDto =
            PostDto(id, "", "")

    override fun toDto(keyword: String): PostKeywordDto =
            PostKeywordDto(keyword)

    override fun toEntity(dto: PostDto, user: User): Post =
            Post(-1, dto.title, dto.content, user)

    override fun toQueryDto(post: Post, comment: MutableList<String>, isMine: Boolean): PostQueryDto =
            PostQueryDto(post.id, isMine, post.title, post.content, comment)

    override fun toResponse(dto: PostQueryDto): PostResponse =
            PostResponse(dto.id, dto.isMine, dto.title, dto.content, dto.comment)

}