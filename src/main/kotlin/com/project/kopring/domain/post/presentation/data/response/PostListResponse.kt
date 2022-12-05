package com.project.kopring.domain.post.presentation.data.response

import com.project.kopring.domain.post.presentation.data.dto.PostQueryDto

data class PostListResponse(
    val list: List<PostQueryDto>
)