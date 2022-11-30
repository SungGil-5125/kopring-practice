package com.project.kopring.domain.post.service

import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.post.presentation.dto.request.PostRequest
import com.project.kopring.domain.post.presentation.dto.request.UpdatePostRequest
import com.project.kopring.domain.post.presentation.dto.response.PostListResponse
import com.project.kopring.domain.post.presentation.dto.response.PostResponse

interface PostService {

    fun writePost(postRequest: PostRequest)
    fun deletePost(postId: Long)
    fun updatePost(postId: Long, updatePostRequest: UpdatePostRequest)
    fun findPostDetailById(postId: Long): PostResponse
    fun findAllPost(): PostListResponse

}