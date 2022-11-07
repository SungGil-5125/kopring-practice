package com.project.kopring.global.exception

enum class ErrorCode(
        val message: String,
        val status: Int
) {

    // USER
    DUPLICATE_EMAIL("중복된 이메일 입니다.", 400),
    NOT_FOUND_USER("사용자를 찾을 수 없습니다.", 404),
    PASSWORD_NOT_CORRECT("비밀번호가 일치하지 않습니다.", 400),

    // BOARD

}