package com.project.kopring.infrastructure.s3.service.impl

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.project.kopring.infrastructure.s3.config.S3BucketProperties
import com.project.kopring.infrastructure.s3.service.S3Service
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class S3ServiceImpl(
    private val amazonS3: AmazonS3,
    private val s3BucketProperties: S3BucketProperties
) : S3Service {

    override fun uploadFile(file: MultipartFile, dirName: String): String {
        val fileName = createFileName(file.originalFilename.toString())
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = file.size
        objectMetadata.contentType = file.contentType

        runCatching {
            amazonS3.putObject(
                PutObjectRequest(s3BucketProperties.bucket, dirName + fileName, file.inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            )
        }.onFailure {
            throw IllegalArgumentException("파일 업로드에 실패했습니다")
        }

        return fileName
    }

    override fun deleteFile(fileName: String) {
        amazonS3.deleteObject(DeleteObjectRequest(s3BucketProperties.bucket, fileName))
    }

    fun createFileName(fileName: String): String {
        return UUID.randomUUID().toString().plus(getFileExtension(fileName))
    }

    fun getFileExtension(fileName: String): String {
        try {
            return fileName.substring(fileName.lastIndexOf("."))
        } catch (e: StringIndexOutOfBoundsException) {
            throw IllegalArgumentException("잘못된 파일의 형식입니다")
        }
    }
}