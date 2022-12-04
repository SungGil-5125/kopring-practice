package com.project.kopring.domain.post.service

import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.data.dto.PostKeywordDto
import com.project.kopring.domain.post.presentation.data.dto.PostQueryDto

interface PostService {

    fun writePost(postDto: PostDto)
    fun updatePost(postDto: PostDto)
    fun deletePost(postDto: PostDto)
    fun findPostDetailById(postDto: PostDto): PostQueryDto
    fun findAllPost(): List<PostQueryDto>
    fun findPostByKeyword(postKeywordDto: PostKeywordDto): List<PostQueryDto>

}