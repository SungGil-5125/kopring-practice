package com.project.kopring.domain.post.entity

import com.project.kopring.domain.user.entity.User
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Post(
        var title: String,
        var content: String,
        @CreatedDate
        val createdDate: LocalDateTime,
        @ManyToOne
        @JoinColumn(name = "userId")
        val user: User,
        @ElementCollection(fetch = FetchType.LAZY)
        @CollectionTable(name = "post_tags", joinColumns = [JoinColumn(name = "postId")])
        var tags: MutableList<String>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val postId = 0L

    fun updatePost(title: String, content: String, tags: MutableList<String>) {
        this.title = title
        this.content = content
        this.tags = tags
    }
}