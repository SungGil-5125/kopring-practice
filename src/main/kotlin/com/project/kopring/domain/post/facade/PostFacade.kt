package com.project.kopring.domain.post.facade

import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.post.domain.repository.PostRepository
import com.project.kopring.domain.post.exception.PostNotFoundException
import com.project.kopring.domain.post.presentation.dto.request.PostRequest
import com.project.kopring.domain.post.presentation.dto.request.toEntity
import com.project.kopring.domain.post.presentation.dto.response.PostListResponse
import com.project.kopring.domain.post.presentation.dto.response.PostResponse
import com.project.kopring.domain.user.facade.UserFacade
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PostFacade(
        private val postRepository: PostRepository,
        private val userFacade: UserFacade
) {

    @Transactional(rollbackFor = [Exception::class])
    fun savePost(postRequest: PostRequest) = postRepository.save(postRequest.toEntity(userFacade.currentUser()))

    @Transactional(rollbackFor = [Exception::class])
    fun deletePost(postId: Long) = postRepository.delete(findPostById(postId))

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    fun findPostDetailById(postId: Long) = PostResponse(findPostById(postId), isPostMine(findPostById(postId)))

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    fun findAllPosts() = PostListResponse(postRepository.findAll().map { PostResponse(it, isPostMine(it)) })

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    fun findPostById(postId: Long): Post = postRepository.findById(postId).orElseThrow{ throw PostNotFoundException() }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    fun isPostMine(post: Post): Boolean {
        return post.user == userFacade.currentUser();
    }

}