package com.project.kopring.domain.post.domain

import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.user.domain.User
import com.project.kopring.global.entity.BaseTimeEntity
import javax.persistence.*

@Entity
class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val postId: Long,
    var title: String,
    var content: String,
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "post_tags", joinColumns = [JoinColumn(name = "post_id")])
    var tags: MutableList<String>,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
): BaseTimeEntity() {

    fun updatePost(postDto: PostDto) {
        this.title = postDto.title
        this.content = postDto.description
        this.tags = postDto.tags
    }

}