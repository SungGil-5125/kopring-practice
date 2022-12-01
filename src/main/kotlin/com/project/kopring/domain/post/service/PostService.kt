package com.project.kopring.domain.post.service

import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.data.request.PostRequest
import com.project.kopring.domain.post.presentation.data.request.UpdatePostRequest
import com.project.kopring.domain.post.presentation.data.response.PostListResponse
import com.project.kopring.domain.post.presentation.data.response.PostResponse

interface PostService {

    fun writePost(postDto: PostDto)
    fun deletePost(postDto: PostDto)
    fun updatePost(postDto: PostDto)
    fun findPostDetailById(postDto: PostDto): PostResponse
    fun findAllPost(): PostListResponse

}