package com.project.kopring.domain.comment.domain

import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.user.domain.User
import javax.persistence.*

@Entity
class Comment(
    @Id @GeneratedValue
    @Column(name = "comment_id")
    val id: Long,
    val comment: String,
    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
)