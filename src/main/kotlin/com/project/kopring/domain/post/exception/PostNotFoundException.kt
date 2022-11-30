package com.project.kopring.domain.post.exception

import com.project.kopring.global.error.type.ErrorCode
import com.project.kopring.global.error.exception.BasicException

class PostNotFoundException: BasicException(ErrorCode.POST_NOT_FOUND)
