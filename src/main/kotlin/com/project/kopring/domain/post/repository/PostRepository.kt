package com.project.kopring.domain.post.repository

import com.project.kopring.domain.post.entity.Post
import org.springframework.data.repository.CrudRepository

interface PostRepository: CrudRepository<Post, Long> {
    fun findPostByPostId(postId: Long): Post?
}