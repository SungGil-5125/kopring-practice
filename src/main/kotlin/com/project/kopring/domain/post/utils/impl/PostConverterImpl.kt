package com.project.kopring.domain.post.utils.impl

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
import com.project.kopring.domain.post.utils.PostConverter
import com.project.kopring.domain.user.domain.User
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class PostConverterImpl : PostConverter {

    override fun toDto(request: PostRequest, file: MultipartFile): PostDto =
        PostDto(-1, request.title, request.content, request.tags, file)

    override fun toDto(id: Long, request: UpdatePostRequest, file: MultipartFile): PostDto =
        PostDto(id, request.title, request.content, request.tags, file)

    override fun toDto(id: Long): PostDto =
        PostDto(id, "", "", Collections.emptyList(), null)

    override fun toDto(keyword: String): PostKeywordDto =
        PostKeywordDto(keyword)

    override fun toEntity(dto: PostDto, user: User, uploadFile: String): Post =
        Post(-1, dto.title, dto.content, dto.tags, "https://devlog-v2-bucket.s3.ap-northeast-2.amazonaws.com/POST/".plus(uploadFile), user)

    override fun toQueryDto(post: Post, comment: MutableList<CommentQueryDto>, isMine: Boolean): PostQueryDto =
        PostQueryDto(post.id, post.user.userId, isMine, post.title, post.content, post.tags, post.imageUrl, comment)

    override fun toResponse(dto: PostQueryDto): PostResponse =
        PostResponse(dto.postId, dto.userId, dto.isMine, dto.title, dto.content, dto.tags, dto.imageUrl, dto.comments)

    override fun toListResponse(dto: PostListQueryDto): PostListResponse =
        PostListResponse(dto.list)

}