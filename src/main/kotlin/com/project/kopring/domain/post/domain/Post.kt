package com.project.kopring.domain.post.domain

import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.user.domain.User
import com.project.kopring.global.entity.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    val id: Long,
    var title: String,
    var content: String,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
): BaseTimeEntity() {

    fun updatePost(postDto: PostDto) {
        this.title = postDto.title
        this.content = postDto.content
    }

}