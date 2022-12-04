package com.project.kopring.domain.comment.domain

import com.project.kopring.domain.post.domain.Post
import javax.persistence.*

@Entity
class Comment(
    @Id @GeneratedValue
    @Column(name = "comment_id")
    val id: Long,
    val comment: String,
    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post
)