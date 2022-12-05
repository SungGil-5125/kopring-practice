package com.project.kopring.domain.comment.exception

import com.project.kopring.global.error.exception.BasicException
import com.project.kopring.global.error.type.ErrorCode

class CommentNotFoundException : BasicException(ErrorCode.COMMENT_NOT_FOUND)