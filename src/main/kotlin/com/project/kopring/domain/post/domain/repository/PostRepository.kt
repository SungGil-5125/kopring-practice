package com.project.kopring.domain.post.domain.repository

import com.project.kopring.domain.post.domain.Post
import org.springframework.data.repository.CrudRepository

interface PostRepository: CrudRepository<Post, Long> {
    fun findPostById(postId: Long): Post?
    fun findPostByTitleContaining(title: String): List<Post>
}