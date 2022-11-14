package com.project.kopring.domain.post.service.Impl

import com.project.kopring.domain.post.entity.Post
import com.project.kopring.domain.post.exception.PostNotFoundException
import com.project.kopring.domain.post.presentation.dto.request.PostRequest
import com.project.kopring.domain.post.presentation.dto.request.UpdatePostRequest
import com.project.kopring.domain.post.presentation.dto.response.PostListResponse
import com.project.kopring.domain.post.presentation.dto.response.PostResponse
import com.project.kopring.domain.post.repository.PostRepository
import com.project.kopring.domain.post.service.PostService
import com.project.kopring.domain.user.util.UserUtil
import com.project.kopring.global.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
        private val postRepository: PostRepository,
        private val userUtil: UserUtil
): PostService {

    @Transactional(rollbackFor = [Exception::class])
    override fun writePost(postRequest: PostRequest) {
        postRepository.save(postRequest.toEntity(userUtil.currentUser()))
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deletePost(postId: Long) {
        postRepository.delete(findPostById(postId))
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updatePost(postId: Long, updatePostRequest: UpdatePostRequest) {
        val post = findPostById(postId)
        post.updatePost(updatePostRequest.title, updatePostRequest.content, updatePostRequest.tags)
    }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findPostDetailById(postId: Long): PostResponse {
        val post = findPostById(postId);
        return PostResponse(post)
    }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findAllPost(): PostListResponse {
        val posts = postRepository.findAll()
        return PostListResponse(
                posts.map { PostResponse(it) }
        )
    }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findPostById(postId: Long): Post = postRepository.findById(postId).orElseThrow{ throw PostNotFoundException(ErrorCode.POST_NOT_FOUND) }

}