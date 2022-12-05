package com.project.kopring.domain.comment.domain

import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.user.domain.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
class Comment(
    @Id @GeneratedValue
    @Column(name = "comment_id")
    val id: Long,
    var comment: String,
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id")
    val post: Post,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
) {
    fun updateComment(comment: String) {
        this.comment = comment
    }
}