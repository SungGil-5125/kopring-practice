package com.project.kopring.domain.post.service.Impl

import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.post.domain.repository.PostRepository
import com.project.kopring.domain.post.exception.PostNotFoundException
import com.project.kopring.domain.post.facade.PostFacade
import com.project.kopring.domain.post.presentation.dto.request.PostRequest
import com.project.kopring.domain.post.presentation.dto.request.UpdatePostRequest
import com.project.kopring.domain.post.presentation.dto.request.toEntity
import com.project.kopring.domain.post.presentation.dto.response.PostListResponse
import com.project.kopring.domain.post.presentation.dto.response.PostResponse
import com.project.kopring.domain.post.service.PostService
import com.project.kopring.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
        private val postFacade: PostFacade,
): PostService {

    @Transactional(rollbackFor = [Exception::class])
    override fun writePost(postRequest: PostRequest) {
        postFacade.savePost(postRequest)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deletePost(postId: Long) {
        postFacade.deletePost(postId)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updatePost(postId: Long, updatePostRequest: UpdatePostRequest) {
        val post = postFacade.findPostById(postId)
        post.updatePost(updatePostRequest.title, updatePostRequest.content, updatePostRequest.tags)
    }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findPostDetailById(postId: Long): PostResponse {
        return postFacade.findPostDetailById(postId)
    }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findAllPost(): PostListResponse {
        return postFacade.findAllPosts();
    }

}