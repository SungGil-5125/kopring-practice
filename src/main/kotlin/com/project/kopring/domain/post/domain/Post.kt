package com.project.kopring.domain.post.domain

import com.project.kopring.domain.user.domain.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Post(
        var title: String,
        var content: String,
        @CreatedDate
        val createdDate: LocalDateTime,
        @ManyToOne
        @JoinColumn(name = "user_id")
        val user: User,
        @ElementCollection(fetch = FetchType.LAZY)
        @CollectionTable(name = "post_tags", joinColumns = [JoinColumn(name = "post_id")])
        var tags: MutableList<String>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    val postId = 0L

    fun updatePost(title: String, content: String, tags: MutableList<String>) {
        this.title = title
        this.content = content
        this.tags = tags
    }
}