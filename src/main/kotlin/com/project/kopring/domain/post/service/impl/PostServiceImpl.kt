package com.project.kopring.domain.post.service.impl

import com.project.kopring.domain.comment.domain.Comment
import com.project.kopring.domain.comment.domain.repository.CommentRepository
import com.project.kopring.domain.comment.presentation.data.dto.CommentQueryDto
import com.project.kopring.domain.post.domain.Post
import com.project.kopring.domain.post.domain.repository.PostRepository
import com.project.kopring.domain.post.exception.PostNotFoundException
import com.project.kopring.domain.post.presentation.data.dto.PostDto
import com.project.kopring.domain.post.presentation.data.dto.PostKeywordDto
import com.project.kopring.domain.post.presentation.data.dto.PostListQueryDto
import com.project.kopring.domain.post.presentation.data.dto.PostQueryDto
import com.project.kopring.domain.post.presentation.data.type.PostValidatorType
import com.project.kopring.domain.post.service.PostService
import com.project.kopring.domain.post.utils.PostConverter
import com.project.kopring.domain.post.utils.PostValidator
import com.project.kopring.domain.user.utils.UserUtil
import com.project.kopring.infrastructure.s3.service.S3Service
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val postConverter: PostConverter,
    private val postValidator: PostValidator,
    private val commentRepository: CommentRepository,
    private val userUtil: UserUtil,
    private val s3Service: S3Service
) : PostService {

    @Transactional(rollbackFor = [Exception::class])
    override fun writePost(postDto: PostDto) {
        val uploadFile = s3Service.uploadFile(postDto.file!!, "POST/")
        postConverter.toEntity(postDto, userUtil.currentUser(), uploadFile)
            .let { postRepository.save(it) }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updatePost(postDto: PostDto) {
        val uploadFile = postDto.file
            ?.let { s3Service.uploadFile(it, "POST/") }
        postValidator.validate(PostValidatorType.UPDATE, postDto)
            .let {
                if (uploadFile != null) it.updatePost(postDto, uploadFile)
                else it.updatePost(postDto, it.imageUrl)
            }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deletePost(postDto: PostDto) {
        postValidator.validate(PostValidatorType.DELETE, postDto)
            .let {
                postRepository.deleteById(it.id)
                s3Service.deleteFile(it.imageUrl.substring(57))
            }
    }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findPostDetailById(postDto: PostDto): PostQueryDto =
        postRepository.findPostById(postDto.id)
            .let { it ?: throw PostNotFoundException() }
            .let { postConverter.toQueryDto(it, findCommentByPostId(it.id), isPostMine(it)) }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findAllPost(): PostListQueryDto =
        postRepository.findAll()
            .map { postConverter.toQueryDto(it, findCommentByPostId(it.id), isPostMine(it)) }
            .let { PostListQueryDto(it) }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun findPostByKeyword(postKeywordDto: PostKeywordDto): PostListQueryDto {
        return postRepository.findPostByTitleContaining(postKeywordDto.keyword)
            .map { postConverter.toQueryDto(it, findCommentByPostId(it.id), isPostMine(it)) }
            .let { PostListQueryDto(it) }
    }

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    fun findCommentByPostId(postId: Long) =
        commentRepository.findCommentByPostId(postId)
            .map {
                CommentQueryDto(
                    it.id,
                    it.comment,
                    isCommentMine(it),
                    CommentQueryDto.UserInfo(it.user.userId, it.user.name, it.user.imageUrl)
                )
            }.toMutableList()

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    fun isPostMine(post: Post): Boolean = post.user.email == userUtil.currentUser().email

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    fun isCommentMine(comment: Comment) = comment.user.email == userUtil.currentUser().email

}